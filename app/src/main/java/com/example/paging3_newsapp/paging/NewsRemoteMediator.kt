package com.example.paging3_newsapp.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging3_newsapp.api.NewsApi
import com.example.paging3_newsapp.model.Article
import com.example.paging3_newsapp.room.ArticleRemoteKey
import com.example.paging3_newsapp.room.NewsDatabase
import com.example.paging3_newsapp.room.daos.ArticleDao
import com.example.paging3_newsapp.room.daos.ArticleRemoteKeysDao
import com.example.paging3_newsapp.utils.Constants.API_KEY
import java.io.InvalidObjectException


@ExperimentalPagingApi
class NewsRemoteMediator(
    private val database: NewsDatabase,
    private val newsApi: NewsApi,
    private val newsArticleDao: ArticleDao,
    private val newsArticleRemoteKeysDao: ArticleRemoteKeysDao,
    private val initialPage: Int = 1
) : RemoteMediator<Int, Article>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {


        return try {

            // Judging the page number
            val page = when (loadType) {
                LoadType.APPEND -> {

                    val remoteKey =
                        getLastRemoteKey(state) ?: throw InvalidObjectException("Inafjklg")
                    remoteKey.next ?: return MediatorResult.Success(endOfPaginationReached = true)

                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.REFRESH -> {
                    val remoteKey = getClosestRemoteKeys(state)
                    remoteKey?.next?.minus(1) ?: initialPage
                }
            }

            // make network request
            val response = newsApi.getAllSportsNews(
                "in",
                "sports",
                API_KEY,
                page,
                state.config.pageSize
            )
            val endOfPagination = response.body()?.articles?.size!! < state.config.pageSize
            Log.d("articles",response.body()?.articles!!.toString())
            val responseResults = response.body()?.articles

            if (response.isSuccessful) {

                response.body()?.let {

                    // flush our data

                        if (loadType == LoadType.REFRESH) {

                            newsArticleRemoteKeysDao.deleteAllRemoteKeys()
                            newsArticleDao.deleteAllArticles()
                        }


                        val prev = if (page == initialPage) null else page - 1
                        val next = if (endOfPagination) null else page + 1


                        val list = response.body()?.articles?.map {
                            ArticleRemoteKey(it.title, prev, next)
                        }


                        // make list of remote keys

                        if (list != null) {
                           newsArticleRemoteKeysDao.insertAllRemoteKeys(list)

                        }

                        // insert to the room
                        newsArticleDao.insertArticles(responseResults!!)





                }
                MediatorResult.Success(endOfPagination)
            } else {
                MediatorResult.Success(endOfPaginationReached = true)
            }


        } catch (e: Exception) {
            MediatorResult.Error(e)
        }

    }

    private suspend fun getClosestRemoteKeys(state: PagingState<Int, Article>): ArticleRemoteKey? {

        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let {
                newsArticleRemoteKeysDao.getAllRemoteKeys(it.title)
            }
        }

    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Article>): ArticleRemoteKey? {
        return state.lastItemOrNull()?.let {
            newsArticleRemoteKeysDao.getAllRemoteKeys(it.title)

        }
    }

}