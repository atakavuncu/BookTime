package com.atakavuncu.booktime.data.api

import com.atakavuncu.booktime.data.model.book.Description
import com.atakavuncu.booktime.data.model.book.SearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenLibraryApi {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("title") title: String,
    ) : SearchResponse

    @GET("search.json")
    suspend fun getBookDetails(
        @Query("q") bookId: String
    ) : SearchResponse

    @GET("works/{bookId}.json")
    suspend fun getBookDescription(
        @Path("bookId") bookId: String
    ) : Description

    companion object {
        private const val BASE_URL = "https://openlibrary.org/"

        fun create(): OpenLibraryApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(OpenLibraryApi::class.java)
        }
    }
}