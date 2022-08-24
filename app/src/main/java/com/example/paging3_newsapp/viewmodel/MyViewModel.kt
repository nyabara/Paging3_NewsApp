package com.example.paging3_newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.paging3_newsapp.api.NewsApi
import com.example.paging3_newsapp.model.Article
import com.example.paging3_newsapp.paging.NewsRemoteMediator
import com.example.paging3_newsapp.room.NewsDatabase
import com.example.paging3_newsapp.room.daos.ArticleDao
import com.example.paging3_newsapp.room.daos.ArticleRemoteKeysDao
import com.example.paging3_newsapp.room.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MyViewModel
@OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    private val newsRepository: Repository,
    val articleDao: ArticleDao,
    val articleRemoteKeysDao: ArticleRemoteKeysDao,
    val database: NewsDatabase,
    val newsApi: NewsApi
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val list: Flow<PagingData<Article>> = newsRepository.getAllNewsStream()

    val articlesDao = {articleDao.getAllArticles()}
    @OptIn(ExperimentalPagingApi::class)
    val pager:Flow<PagingData<Article>> = Pager(
        PagingConfig(pageSize = 10),
        remoteMediator =
        NewsRemoteMediator(
            newsApi = newsApi,
            newsArticleDao = articleDao,
            database = database,
            newsArticleRemoteKeysDao = articleRemoteKeysDao,
            initialPage = 1
        ),
           pagingSourceFactory = articlesDao

    ) .flow
        .cachedIn(viewModelScope)
}