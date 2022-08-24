package com.example.paging3_newsapp.room.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paging3_newsapp.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(list: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleSingle(article: Article)

    @Query("SELECT * FROM news_table ")
    fun getAllArticles(): PagingSource<Int, Article>

    @Query("DELETE FROM news_table")
    suspend fun deleteAllArticles()
}