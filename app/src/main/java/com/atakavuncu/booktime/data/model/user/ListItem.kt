package com.atakavuncu.booktime.data.model.user

import com.atakavuncu.booktime.data.model.book.ExistingBook

data class ListItem(
    val books: List<ExistingBook>? = null,
    val listName: String? = ""
)
