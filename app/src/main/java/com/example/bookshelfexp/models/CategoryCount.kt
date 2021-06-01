package com.example.bookshelfexp.models

import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

/**
 * Created by SARATH on 30-03-2021
 */
 data class CategoryCount(
        @ColumnInfo(name="categories")
        var categories:List<String>,
        @ColumnInfo(name="COUNT(*)")
        var num:Int,
        @ColumnInfo(name="categoryState")
        var categoryState : Boolean = false
 )