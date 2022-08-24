package com.example.paging3_newsapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paging3_newsapp.utils.Constants.NEWS_REMOTE_KEYS_TABLE

@Entity(tableName = NEWS_REMOTE_KEYS_TABLE)
data class ArticleRemoteKey (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prev: Int?,
    val next: Int?
)