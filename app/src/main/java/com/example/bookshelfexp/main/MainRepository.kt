package com.example.bookshelfexp.main

import com.example.bookshelfexp.models.BookResponseItem
import com.example.bookshelfexp.models.CategoryCount
import com.example.bookshelfexp.util.Resource

interface MainRepository {

    suspend fun getAllBooks() : Resource<List<BookResponseItem>>

    fun getDistinctCategory():Resource<List<CategoryCount>>

    fun getAllBooksInDb() : Resource<List<BookResponseItem>>

    suspend fun saveFavouriteBooks(bookResponseItem: BookResponseItem)

    fun getBookAndUpdate(query : List<String>) : List<BookResponseItem>

    fun getFavouriteBooks() : Resource<List<BookResponseItem>>

    suspend fun deleteBook(bookResponseItem: BookResponseItem)

    fun searchBookItem(key : String) : Resource<List<BookResponseItem>>


}