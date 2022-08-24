package com.example.paging3_newsapp.hilt

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.example.paging3_newsapp.api.NewsApi
import com.example.paging3_newsapp.room.NewsDatabase
import com.example.paging3_newsapp.room.repository.Repository
import com.example.paging3_newsapp.utils.Constants.NEWS_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            NEWS_DATABASE
        ).build()
    }


    @Provides
    fun provideArticleDao(database: NewsDatabase) = database.articleDao()

    @Provides
    fun provideArticleRemoteKeysDao(database: NewsDatabase) = database.articleRemoteKeysDao()



}