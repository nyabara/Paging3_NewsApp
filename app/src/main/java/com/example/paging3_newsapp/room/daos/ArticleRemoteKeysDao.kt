package com.example.paging3_newsapp.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paging3_newsapp.room.ArticleRemoteKey

@Dao
interface ArticleRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(list: List<ArticleRemoteKey>)

    @Query("SELECT * FROM news_remote_keys_table WHERE id = :id")
    suspend fun getAllRemoteKeys(id: String): ArticleRemoteKey?

    @Query("DELETE FROM news_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}