package com.atakavuncu.booktime.data.repository

import com.atakavuncu.booktime.data.model.UserModel

class UserRepository {
    private var loggedInUser: UserModel? = null

    fun setLoggedInUser(user: UserModel){
        loggedInUser = user
    }

    fun getLoggedInUser(): UserModel?{
        return loggedInUser
    }

    fun clearLoggedInUser(){
        loggedInUser = null
    }
}