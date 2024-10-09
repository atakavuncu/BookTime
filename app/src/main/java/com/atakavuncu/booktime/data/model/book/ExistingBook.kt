package com.atakavuncu.booktime.data.model.book

data class ExistingBook(
    val bookId: String? = "",
    val coverUrl: String? = "",
    val title: String? = "",
    val authorName: String = "",
    val readPageCount: Int? = 0,
    val bookPageCount: Int? = 0,
    val status: String? = ""
)
