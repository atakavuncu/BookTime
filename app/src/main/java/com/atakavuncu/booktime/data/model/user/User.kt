package com.atakavuncu.booktime.data.model.user

import com.atakavuncu.booktime.data.model.book.ExistingBook

data class User(
    val username: String? = "",
    val profilePhotoUrl: String? = "",
    val coverPhotoUrl: String? = "",
    val favoriteBooks: List<ExistingBook>? = emptyList(),
    val lists: List<ListItem>? = emptyList(),
    val books: List<ExistingBook>? = emptyList()
)
