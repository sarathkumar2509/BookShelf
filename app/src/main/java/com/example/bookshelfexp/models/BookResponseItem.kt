package com.example.bookshelfexp.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(
    tableName = "books"
)
data class BookResponseItem(
        @PrimaryKey(autoGenerate = true)
        @SerializedName("id")
        var id: Int,
        @SerializedName("authors")
        val authors: List<String>,
        @SerializedName("categories")
        val categories: List<String>,
        @SerializedName("isbn")
        val isbn: String?,
        @SerializedName("longDescription")
        val longDescription: String?,
        @SerializedName("pageCount")
        val pageCount: Int?,
        @SerializedName("publishedDate")
        val publishedDate: PublishedDate?,
        @SerializedName("shortDescription")
        val shortDescription: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("thumbnailUrl")
        val thumbnailUrl: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("categoryState")
        var categoryState: Boolean = false,
        @SerializedName("isFavourite")
        var favourite: Boolean = false
) : Serializable