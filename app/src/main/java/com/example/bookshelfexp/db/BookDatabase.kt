package com.example.bookshelfexp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bookshelfexp.models.BookResponseItem

@Database(
    entities = [BookResponseItem::class],
    version = 2
)
@TypeConverters(Converters::class)

abstract class BookDatabase : RoomDatabase() {

    abstract fun getBookDao() : BookDao

}