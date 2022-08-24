package com.example.paging3_newsapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.paging3_newsapp.RoomTypeConverter
import com.example.paging3_newsapp.model.Article
import com.example.paging3_newsapp.room.daos.ArticleDao
import com.example.paging3_newsapp.room.daos.ArticleRemoteKeysDao

@Database(entities = [Article::class, ArticleRemoteKey::class], version = 1)
@TypeConverters(RoomTypeConverter::class)

abstract class NewsDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun articleRemoteKeysDao(): ArticleRemoteKeysDao

}