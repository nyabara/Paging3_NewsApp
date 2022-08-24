package com.example.paging3_newsapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3_newsapp.api.NewsApi
import com.example.paging3_newsapp.model.Article
import com.example.paging3_newsapp.utils.Constants
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(private val newsApi: NewsApi) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        return try {

            val position =
                if (params.key == null) {
                    1
                } else {
                    params.key
                }

            val data = newsApi.getAllNews(
                "in",
                "business",
                Constants.API_KEY,
                position!!,
                params.loadSize
            )
            LoadResult.Page(
                data = data.articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (data.articles.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }


    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }
}