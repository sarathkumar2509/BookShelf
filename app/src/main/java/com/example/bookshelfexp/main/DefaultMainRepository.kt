package com.example.bookshelfexp.main

import android.util.Log
import com.example.bookshelfexp.db.BookDatabase
import com.example.bookshelfexp.models.BookResponseItem
import com.example.bookshelfexp.models.CategoryCount
import com.example.bookshelfexp.models.api.BookApi
import com.example.bookshelfexp.util.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: BookApi,
    private val db: BookDatabase
) : MainRepository {
    override suspend fun getAllBooks(): Resource<List<BookResponseItem>> {
        return try {
            val response = api.getAllBooks()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An Error Occurred")
        }
    }

    override  fun getDistinctCategory(): Resource<List<CategoryCount>> {
        val categoryCount = db.getBookDao().getDistinctCategoryCount()
        return Resource.Success(categoryCount)


    }

    override fun getAllBooksInDb(): Resource<List<BookResponseItem>> {

        val list =  db.getBookDao().getAllBooks()
        return Resource.Success(list)

    }

    override suspend fun saveFavouriteBooks(bookResponseItem: BookResponseItem) {
        bookResponseItem.favourite= true
        db.getBookDao().saveFavouriteBooks(bookResponseItem)
    }

    override fun getBookAndUpdate(query: List<String>) : List<BookResponseItem> {
       val list =  db.getBookDao().getBookByQuery(query)
        return list
    }

    override fun getFavouriteBooks(): Resource<List<BookResponseItem>> {
        val favourites = db.getBookDao().getFavouriteBooks()
        Log.d("FAVOURITEFRAGMENT","$favourites")
        return Resource.Success(favourites)
    }

    override suspend fun deleteBook(bookResponseItem: BookResponseItem) {
        bookResponseItem.favourite = false
        db.getBookDao().deleteBook(bookResponseItem)
    }

    override fun searchBookItem(key: String): Resource<List<BookResponseItem>> {
        var item = db.getBookDao().searchQuery(key)
        return Resource.Success(item)
    }


//    override suspend fun insertAllBooks(list: List<BookResponseItem>) : Resource<Long> {
//        db.getBookDao().insert(list)
//        return Resource.Success(1)
//    }
}