package com.example.instagramclone.paging

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.instagramclone.ListStoryItem
import com.example.instagramclone.adapters.dataStore
import com.example.instagramclone.database.RemoteKeys
import com.example.instagramclone.database.StoryDatabase
import com.example.instagramclone.network.ApiService


@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val database: StoryDatabase,
    private val apiService: ApiService,
    private val context: Context,
    private val token: String
) : RemoteMediator<Int, ListStoryItem>() {

    private val dataStore = context.dataStore


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListStoryItem>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }


        return try {
//            Log.d(TAG, "load: BANGGG")
//            dataStore.data.map { preferences ->
//                Log.d(TAG, "load: TEST")
//                Log.d(TAG, "load: ${preferences[stringPreferencesKey("token_setting")]}")
//            }
//
//
//            val token = dataStore.data.map { preferences ->
//                preferences[stringPreferencesKey("token_setting")] ?: ""
//            }.first()

//            val token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVZvSUluS2NYdXVpcFZ2cFEiLCJpYXQiOjE2ODUzODAzMzF9.p48OuTFYY02l5OBFCz9GbwG92XvhxeqmUsnriQn0M70"
            val responseData = apiService.getAllStories("Bearer ${token}",page, state.config.pageSize)
            Log.d("MODAN", "load: ${responseData.listStory.toString()}")
            val endOfPaginationReached = responseData.listStory.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.storiesDao()
                    database.remoteKeysDao().deleteRemoteKeys()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                Log.d(TAG, "load: $page")
                val keys = responseData.listStory.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.storiesDao().insertStories(responseData.listStory)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ListStoryItem>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ListStoryItem>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ListStoryItem>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }


    private companion object {
        const val TAG = "thoriq"
        const val INITIAL_PAGE_INDEX = 1
    }

}