package com.atakavuncu.booktime.ui.bookdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atakavuncu.booktime.data.model.book.BookDetails
import com.atakavuncu.booktime.data.model.book.ExistingBook
import com.atakavuncu.booktime.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {
    private val _book = MutableStateFlow<BookDetails?>(null)
    val book: StateFlow<BookDetails?> = _book

    private val _existingBook = MutableStateFlow<ExistingBook?>(null)
    val existingBook: StateFlow<ExistingBook?> = _existingBook

    private val _isFavorite = MutableStateFlow<Boolean?>(false)
    val isFavorite: StateFlow<Boolean?> = _isFavorite

    fun getBookDetails(bookId: String) {
        viewModelScope.launch {
            val bookDetails = repository.getBookDetails(bookId)
            _book.value = bookDetails
            if (bookDetails == null) {
                Log.e("BookDetailViewModel", "Failed to fetch book details for bookId: $bookId")
            } else {
                Log.d("BookDetailViewModel", "Fetched book details for bookId: $bookId")
            }
        }
    }

    fun addNewBook(existingBook: ExistingBook) {
        viewModelScope.launch {
            repository.addNewBook(existingBook)
        }
    }

    fun deleteBook(bookId: String) {
        viewModelScope.launch {
            repository.deleteBook(bookId)
        }
    }

    fun updateBook(updatedBook: ExistingBook, onCompletion: ((Boolean) -> Unit)? = null) {
        viewModelScope.launch {
            val result = repository.updateBook(updatedBook)
            if (onCompletion != null) {
                onCompletion(result)
            }
        }
    }

    fun getExistingBookById(bookId: String) {
        viewModelScope.launch {
            _existingBook.value = repository.getExistingBookById(bookId)
        }
    }

    fun isBookFavorite(bookId: String) {
        viewModelScope.launch {
            _isFavorite.value = repository.isBookFavorite(bookId)
        }
    }

    fun addFavoriteBook(newBook: ExistingBook) {
        viewModelScope.launch {
            repository.addFavoriteBook(newBook)
            _isFavorite.value = true
        }
    }

    fun deleteFavoriteBook(bookId: String) {
        viewModelScope.launch {
            repository.deleteFavoriteBook(bookId)
            _isFavorite.value = false
        }
    }

}