package com.example.paging3_newsapp.room.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.paging3_newsapp.api.NewsApi
import com.example.paging3_newsapp.model.Article
import com.example.paging3_newsapp.paging.NewsPagingSource
import com.example.paging3_newsapp.paging.NewsRemoteMediator
import com.example.paging3_newsapp.room.NewsDatabase
import com.example.paging3_newsapp.room.daos.ArticleDao
import com.example.paging3_newsapp.room.daos.ArticleRemoteKeysDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val newsApi: NewsApi
) {

    fun getAllNewsStream(): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(
            20,
            5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            NewsPagingSource(newsApi)
        }
    ).flow

}