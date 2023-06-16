package com.example.cooke.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
//import androidx.paging.*
import com.example.cooke.ListStoryItem
import com.example.cooke.network.ApiService
import com.example.cooke.paging.StoryRemoteMediator


class StoryRepository(
    private val database: StoryDatabase,
    private val apiServices: ApiService,
    private val context: Context,
    private val token: String
) {

    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(database, apiServices, context, token),
            pagingSourceFactory = {
                database.storiesDao().getAllStories()
            }
        ).liveData
    }

    fun getStoriesLocation(): LiveData<List<ListStoryItem>> {
        return database.storiesDao().getStoriesLocation().asLiveData()
    }
}