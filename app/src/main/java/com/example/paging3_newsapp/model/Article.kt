package com.example.paging3_newsapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paging3_newsapp.utils.Constants.NEWS_TABLE
import com.google.gson.annotations.SerializedName

import kotlinx.parcelize.Parcelize

@Entity(tableName = NEWS_TABLE)
data class Article(
    @field:SerializedName("author")val author: String?,
    @field:SerializedName("content")val content: String?,
    @field:SerializedName("description")val description: String?,
    @field:SerializedName("publishedAt")val publishedAt: String?,
    @field:SerializedName("source")val source: Source?,
    @PrimaryKey@field:SerializedName("title") val title: String,
    @field:SerializedName("url")val url: String?,
    @field:SerializedName("urlToImage")val urlToImage: String?
)
