package com.atakavuncu.booktime.data.model.book

data class Book(
    val id: String,
    val title: String?,
    val authorName: List<String>?,
    val coverUrl: String?,
    val pageCount: Int?,
    val year: Int?,
    val subject: List<String>?,
)