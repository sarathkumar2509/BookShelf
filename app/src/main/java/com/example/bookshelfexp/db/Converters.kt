package com.example.bookshelfexp.db

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.example.bookshelfexp.models.PublishedDate
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

class Converters {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromDate(publishedDate : PublishedDate) : String?{
        val type = object : TypeToken<PublishedDate>() {}.type
        return Gson().toJson(publishedDate,type)
    }

    @TypeConverter
    fun toDate(date : String) : PublishedDate{
        val type =  object : TypeToken<PublishedDate>() {}.type
        return Gson().fromJson<PublishedDate>(date,type)
    }

    @TypeConverter
    fun fromAuthors(authors : List<String>) : String{
        val type = object  : TypeToken<List<String>>() {}.type
        return Gson().toJson(authors,type)
    }

    @TypeConverter
    fun toAuthors(authors: String) : List<String>{
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson<List<String>>(authors,type)
    }


//    @TypeConverter
//    fun fromCategories(categories: List<String>) : String{
//        val type = object  : TypeToken<List<String>>() {}.type
//        return Gson().toJson(categories,type)
//    }
//
//    @TypeConverter
//    fun toCategories(categories: String) : List<String>{
//        val type = object : TypeToken<List<String>>() {}.type
//        return Gson().fromJson<List<String>>(categories,type)
//    }

}

