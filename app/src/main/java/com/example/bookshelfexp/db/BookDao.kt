package com.example.bookshelfexp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bookshelfexp.models.BookResponse
import com.example.bookshelfexp.models.BookResponseItem
import com.example.bookshelfexp.models.CategoryCount
import com.example.bookshelfexp.util.Resource


@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookResponseItem : BookResponseItem)

    @Query("SELECT  DISTINCT categories , COUNT(*) , categoryState  FROM books GROUP BY categories ")
     fun getDistinctCategoryCount():List<CategoryCount>

     @Query("SELECT * FROM books")
     fun getAllBooks() : List<BookResponseItem>

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun saveFavouriteBooks(bookResponseItem: BookResponseItem)

     @Query("SELECT * FROM books WHERE categories = :key")
     fun getBookByQuery(key : List<String>) : List<BookResponseItem>

     @Query("SELECT * FROM books WHERE favourite = '1'")
     fun getFavouriteBooks() : List<BookResponseItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun deleteBook(bookResponseItem: BookResponseItem)

    @Ignore
    @Query("SELECT * FROM books WHERE longDescription LIKE :key")
    fun searchQuery(key : String) : List<BookResponseItem>


//    @Update
//    suspend fun updateCategoryShowState(categoryName : List<String>)


//    @Delete
//    suspend fun deleteBook(bookResponseItem: BookResponseItem) : Long

}
