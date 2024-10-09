package com.atakavuncu.booktime.data.repository

import android.net.Uri
import android.util.Log
import com.atakavuncu.booktime.data.model.user.User
import com.atakavuncu.booktime.utils.SessionManager
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val sessionManager: SessionManager,
    private val firestore: FirebaseFirestore
) {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    suspend fun loginUser(email: String, password: String): FirebaseUser? {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user?.let { sessionManager.saveUserSession(it) }
            Log.d("LoginUser", "isLoggedIn: ${sessionManager.isLoggedIn()}")
            return result.user
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun registerUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let { sessionManager.saveUserSession(it) }
            Log.d("LoginUser", "isLoggedIn: ${sessionManager.isLoggedIn()}")
            result.user
        } catch (e: Exception) {
            null
        }
    }

    fun logOut() {
        sessionManager.clearSession()
        firebaseAuth.signOut()
    }

    fun getUserId(): String? {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.uid
    }


    suspend fun getUserData(userId: String): User? {
        return try {
            val documentSnapshot = firestore.collection("user").document(userId).get().await()
            documentSnapshot.toObject(User::class.java)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error fetching user data: ", e)
            null
        }
    }

    suspend fun updateEmail(newEmail: String): Boolean {
        return try {
            val user = firebaseAuth.currentUser
            user?.verifyBeforeUpdateEmail(newEmail)?.await()
            if (user != null) {
                sessionManager.saveUserSession(user)

            }
            true
        } catch (e: Exception) {
            Log.e("UserRepository", "Error updating email: ", e)
            false
        }
    }

    suspend fun updatePassword(newPassword: String): Boolean {
        return try {
            val user = firebaseAuth.currentUser
            user?.updatePassword(newPassword)?.await()
            true
        } catch (e: Exception) {
            Log.e("UserRepository", "Error updating password: ", e)
            false
        }
    }

    suspend fun deleteAccount(email: String, password: String): Boolean {
        return try {
            val user = firebaseAuth.currentUser
            val credential = EmailAuthProvider.getCredential(email, password)

            user?.reauthenticate(credential)?.await()

            user?.delete()?.await()
            sessionManager.clearSession()

            true
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("UserRepository", "Error re-authenticating user: ", e)
            false
        } catch (e: Exception) {
            Log.e("UserRepository", "Error deleting account: ", e)
            false
        }
    }

    fun uploadImageToStorage(
        imageUri: Uri,
        userId: String,
        fileName: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        val imageRef = storageRef.child("user_images/$userId/$fileName")

        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    onSuccess(downloadUrl)
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun createUserProfile(username: String, profilePhotoUrl: String?, coverPhotoUrl: String?) {
        try {
            val user = FirebaseAuth.getInstance().currentUser
            val firestore = FirebaseFirestore.getInstance()

            user?.let {
                val userId = it.uid

                Log.d("UserRepository", "profilePhotoUrl: $profilePhotoUrl")
                Log.d("UserRepository", "coverPhotoUrl: $coverPhotoUrl")

                val userProfile = User(
                    username = username,
                    profilePhotoUrl = profilePhotoUrl,
                    coverPhotoUrl = coverPhotoUrl
                )

                firestore.collection("user")
                    .document(userId)
                    .set(userProfile)
                    .addOnSuccessListener {
                        Log.d("UserRepository", "User profile created successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("UserRepository", "Error creating user profile", e)
                    }
            } ?: run {
                Log.e("UserRepository", "User not authenticated")
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error creating user profile", e)
        }
    }

    suspend fun getUserImageUrl(userId: String, isProfilePhoto: Boolean): String? {
        return try {
            val storage = FirebaseStorage.getInstance()
            val storageRef: StorageReference = storage.reference

            val photoPath = if (isProfilePhoto) {
                "user_images/$userId/profile_photo"
            } else {
                "user_images/$userId/cover_photo"
            }

            val photoRef = storageRef.child(photoPath)
            val downloadUrl = photoRef.downloadUrl.await()

            downloadUrl.toString()
        } catch (e: Exception) {
            Log.e("FirebaseStorage", "Error getting user image URL", e)
            null
        }
    }



}