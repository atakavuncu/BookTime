package com.atakavuncu.booktime.ui.auth

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atakavuncu.booktime.data.model.user.User
import com.atakavuncu.booktime.data.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _loginResult = MutableStateFlow<FirebaseUser?>(null)
    val loginResult: StateFlow<FirebaseUser?> = _loginResult

    private val _registerResult = MutableStateFlow<FirebaseUser?>(null)
    val registerResult: StateFlow<FirebaseUser?> = _registerResult

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _bookCount = MutableStateFlow(0)
    val bookCount: StateFlow<Int> = _bookCount

    private val _pageCount = MutableStateFlow(0)
    val pageCount: StateFlow<Int> = _pageCount

    private val _deleteAccountResult = MutableStateFlow<Boolean>(false)
    val deleteAccountResult: StateFlow<Boolean> = _deleteAccountResult

    private val _profilePhotoUrl = MutableStateFlow<String?>("")
    val profilePhotoUrl: StateFlow<String?> = _profilePhotoUrl

    private val _coverPhotoUrl = MutableStateFlow<String?>("")
    val coverPhotoUrl: StateFlow<String?> = _coverPhotoUrl

    private val _userId = MutableStateFlow<String?>("")
    val userId: StateFlow<String?> = _userId

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.loginUser(email, password)
            _loginResult.value = user
        }
    }

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.registerUser(email, password)
            Log.d("UserViewModel", "userId: ${result?.uid}")
            _registerResult.value = result
        }
    }

    fun getUserId() {
        viewModelScope.launch {
            _userId.value = repository.getUserId()
        }
    }


    fun logOut() {
        _loginResult.value = null
        repository.logOut()
    }

    fun fetchUserData(userId: String) {
        viewModelScope.launch {
            val userData = repository.getUserData(userId)
            _user.value = userData
        }
    }

    fun getReadBookCount(userId: String) {
        viewModelScope.launch {
            val userData = repository.getUserData(userId)
            _bookCount.value = 0
            userData?.books?.forEach { book ->
                if (book.status == "Okudum") {
                    _bookCount.value++
                }
            }
        }
    }

    fun getReadPageCount(userId: String) {
        viewModelScope.launch {
            val userData = repository.getUserData(userId)
            _pageCount.value = 0
            userData?.books?.forEach { book ->
                book.readPageCount?.let {
                    _pageCount.value += it
                }
            }
        }
    }

    fun updateEmail(newEmail: String?) {
        viewModelScope.launch {
            if (newEmail != null) {
                repository.updateEmail(newEmail)
            }
        }
    }

    fun updatePassword(newPassword: String?) {
        viewModelScope.launch {
            if (newPassword != null) {
                repository.updatePassword(newPassword)
            }
        }
    }

    fun deleteAccount(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.deleteAccount(email, password)
            _deleteAccountResult.value = result
            if (_deleteAccountResult.value) {
                _loginResult.value = null
            }
        }
    }

    fun uploadImageToStorage(
        imageUri: Uri,
        userId: String,
        fileName: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            repository.uploadImageToStorage(imageUri, userId, fileName, onSuccess, onFailure)
        }
    }

    fun createUserProfile(username: String, profilePhotoUrl: String?, coverPhotoUrl: String?){
        Log.d("UserViewModel", "profilePhotoUrl: $profilePhotoUrl")
        Log.d("UserViewModel", "coverPhotoUrl: $coverPhotoUrl")
        viewModelScope.launch {
            repository.createUserProfile(username, profilePhotoUrl, coverPhotoUrl)
        }
    }

    fun getUserImageUrl(userId: String, isProfilePhoto: Boolean) {
        viewModelScope.launch {
            if (isProfilePhoto) {
                _profilePhotoUrl.value = repository.getUserImageUrl(userId, true)
            } else {
                _coverPhotoUrl.value = repository.getUserImageUrl(userId, false)
            }
        }
    }

}