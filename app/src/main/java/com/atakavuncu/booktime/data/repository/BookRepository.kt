package com.atakavuncu.booktime.data.repository

import android.util.Log
import com.atakavuncu.booktime.data.api.OpenLibraryApi
import com.atakavuncu.booktime.data.model.book.Book
import com.atakavuncu.booktime.data.model.book.BookDetails
import com.atakavuncu.booktime.data.model.book.ExistingBook
import com.atakavuncu.booktime.data.model.book.Description
import com.atakavuncu.booktime.utils.SessionManager
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val api: OpenLibraryApi,
    private val sessionManager: SessionManager,
    private val firestore: FirebaseFirestore
) {
    suspend fun searchBooks(title: String): List<Book> {
        val response = api.searchBooks(title)
        val docs = response.docs ?: return emptyList()
        return docs.map { result ->
            Book(
                id = result.key.split("/").last(),
                title = result.title,
                authorName = result.authorName,
                coverUrl = result.coverId?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
                pageCount = result.pageCount,
                subject = result.subject,
                year = result.year
            )
        }
    }

    suspend fun getBookDetails(bookId: String): BookDetails? {
        return try {
            val detailResponse = api.getBookDetails(bookId)
            val descriptionResponse = api.getBookDescription(bookId)
            val description = descriptionResponse.description ?: ""
            detailResponse.docs?.get(0)?.let {
                BookDetails(
                    book = Book(
                        id = bookId,
                        title = it.title,
                        authorName = it.authorName,
                        coverUrl = it.coverId?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
                        pageCount = it.pageCount,
                        subject = it.subject,
                        year = it.year
                    ),
                    description = Description(description = description)
                )
            }
        } catch (e: Exception) {
            Log.e("BookRepository", "Error fetching book details", e)
            null
        }
    }

    suspend fun addNewBook(newBook: ExistingBook) {
        try {
            val userId = sessionManager.getUserId()
            val userDocument = userId?.let { firestore.collection("user").document(it) }

            userDocument?.update("books", FieldValue.arrayUnion(newBook))?.await()
            Log.d("Firestore", "Kitap başarıyla eklendi!")
        }
        catch (e: Exception) {
            Log.e("Firestore", "Kitap eklenirken hata oluştu: ", e)
        }
    }

    fun deleteBook(bookId: String) {
        try {
            val userId = sessionManager.getUserId()
            val userDocument = userId?.let { firestore.collection("user").document(it) }

            userDocument?.get()?.addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val books = documentSnapshot.get("books") as? List<Map<String, Any>>

                    if (books != null) {
                        val updatedBookField = books.filter { book ->
                            book["bookId"] != bookId
                        }

                        userDocument.update("books", updatedBookField)
                            .addOnSuccessListener {
                                Log.d("Firestore", "Kitap başarıyla silindi.")
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firestore", "Kitap silinirken hata oluştu: ", e)
                            }
                    }
                }
            }?.addOnFailureListener { e ->
                Log.e("Firestore", "Belge getirilirken hata oluştu: ", e)
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Kitap silinirken hata oluştu: ", e)
        }
    }

    suspend fun updateBook(updatedBook: ExistingBook): Boolean {
        return try {
            val userId = sessionManager.getUserId()
            val userDocument = userId?.let { firestore.collection("user").document(it).get().await() }

            val books = userDocument?.get("books") as? MutableList<Map<String, Any>>

            books?.let { bookList ->
                val bookIndex = bookList.indexOfFirst { it["bookId"] == updatedBook.bookId }

                if (bookIndex != -1) {
                    val updatedBookMap = mapOf<String, Any>(
                        "bookId" to updatedBook.bookId!!,
                        "coverUrl" to updatedBook.coverUrl!!,
                        "title" to updatedBook.title!!,
                        "authorName" to updatedBook.authorName!!,
                        "readPageCount" to updatedBook.readPageCount!!,
                        "bookPageCount" to updatedBook.bookPageCount!!,
                        "status" to updatedBook.status!!
                    )
                    bookList[bookIndex] = updatedBookMap

                    firestore.collection("user").document(userId)
                        .update("books", bookList)
                        .await()

                    Log.d("Firestore", "Kitap güncelleme işlemi başarılı")
                    return true
                } else {
                    Log.d("Firestore", "Kitap güncellenirken hata oluştu")
                    return false
                }
            } ?: false

        } catch (e: Exception) {
            Log.e("Firestore", "Kitap güncellenirken hata oluştu: ", e)
            return false
        }
    }


    suspend fun getExistingBookById(bookId: String): ExistingBook? {
        return try {
            var existingBook: ExistingBook? = null
            val userId = sessionManager.getUserId()
            val userDocument = userId?.let { firestore.collection("user").document(userId).get().await() }

            val books = userDocument?.get("books") as? List<Map<String, Any>>
            books?.forEach {
                if (it["bookId"] == bookId) {
                    existingBook = ExistingBook(
                        bookId = it["bookId"].toString(),
                        coverUrl = it["coverUrl"].toString(),
                        title = it["title"].toString(),
                        authorName = it["authorName"].toString(),
                        readPageCount = (it["readPageCount"] as? Long)?.toInt(),
                        bookPageCount = (it["bookPageCount"] as? Long)?.toInt(),
                        status = it["status"].toString()
                    )
                }
            }
            existingBook
        }
        catch (e: Exception) {
            null
        }
    }

    suspend fun isBookFavorite(bookId: String): Boolean {
        try {
            val userId = sessionManager.getUserId()
            val userDocument = userId?.let { firestore.collection("user").document(userId).get().await() }

            val favoriteBooks = userDocument?.get("favoriteBooks") as? List<Map<String, Any>>
            favoriteBooks?.forEach { book ->
                if(book["bookId"] == bookId) {
                    return true
                }
            }
            return false
        }
        catch (e: Exception) {
            Log.e("Firestore", "Favoriler getirilirken bir hata oluştu: ", e)
            return false
        }
    }

    suspend fun  addFavoriteBook(newBook: ExistingBook) {
        try {
            val userId = sessionManager.getUserId()
            val userDocument = userId?.let { firestore.collection("user").document(it) }
            userDocument?.update("favoriteBooks", FieldValue.arrayUnion(newBook))?.await()
        }
        catch (e: Exception) {
            Log.e("Firestore", "Exception in adding new Favorite Book: ", e)
        }
    }

    fun deleteFavoriteBook(bookId: String) {
        try {
            val userId = sessionManager.getUserId()
            val userDocument = userId?.let { firestore.collection("user").document(it) }

            userDocument?.get()?.addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val books = documentSnapshot.get("favoriteBooks") as? List<Map<String, Any>>

                    if (books != null) {
                        val updatedBookField = books.filter { book ->
                            book["bookId"] != bookId
                        }

                        userDocument.update("favoriteBooks", updatedBookField)
                            .addOnSuccessListener {
                                Log.d("Firestore", "Favorite Book successfully added")
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firestore", "Exception in deleting Favorite Book: ", e)
                            }
                    }
                }
            }?.addOnFailureListener { e ->
                Log.e("Firestore", "Exception in fetching document: ", e)
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Exception in deleting Favorite Book: ", e)
        }
    }

}
