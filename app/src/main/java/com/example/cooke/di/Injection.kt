package com.example.cooke.di

import android.content.Context
import com.example.cooke.database.StoryDatabase
import com.example.cooke.database.StoryRepository
import com.example.cooke.network.ApiConfig

object Injection {
    fun provideRepository(context: Context, token: String) : StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService, context, token)
    }

}