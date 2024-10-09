package com.atakavuncu.booktime.data.model.book

import com.google.gson.annotations.SerializedName

data class Docs(
    val key: String,
    val title: String,
    @SerializedName("author_name")
    val authorName: List<String>?,
    @SerializedName("cover_i")
    val coverId: Int?,
    @SerializedName("number_of_pages_median")
    val pageCount: Int?,
    val subject: List<String>?,
    @SerializedName("first_publish_year")
    val year: Int?
)
