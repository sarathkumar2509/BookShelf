package com.example.bookshelfexp.models.api

import com.example.bookshelfexp.models.BookResponse
import com.example.bookshelfexp.models.BookResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    @GET("/books/")
    suspend fun getAllBooks() : Response<List<BookResponseItem>>

}