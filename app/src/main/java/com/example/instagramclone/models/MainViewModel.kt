package com.example.instagramclone.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.instagramclone.ListStoryItem
import com.example.instagramclone.StoryResponse
import com.example.instagramclone.database.StoryRepository
import com.example.instagramclone.network.ApiConfig
import com.example.instagramclone.network.responses.CommunityData
import com.example.instagramclone.network.responses.CommunityItem
import com.example.instagramclone.network.responses.CommunityResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class MainViewModel() : ViewModel() {
    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _listPost = MutableLiveData<List<CommunityItem>>()
    val listPost: LiveData<List<CommunityItem>> = _listPost

//    val stories: LiveData<PagingData<ListStoryItem>> = repository.getStories().cachedIn(viewModelScope)

    companion object{
        private const val TAG = "MainViewModel"
    }

    fun getAllPost(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllPosts("Bearer $token")
        client.enqueue(object : Callback<CommunityResponse> {
            override fun onResponse(
                call: Call<CommunityResponse>,
                response: Response<CommunityResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listPost.value = (response.body()?.data?.community as List<CommunityItem>?)!!
                    Log.d(TAG, "onResponse: ${response.body()?.data}")
                } else {
                    Log.e(TAG, "Unsuccessful response: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<CommunityResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun findStories(token : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getStories("Bearer $token")
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listStory.value = sortListByDate(response.body()?.listStory)
                } else {
                    Log.e(TAG, "Unsuccessful response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun sortListByDate(list : List<ListStoryItem>?): List<ListStoryItem>? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

        val sortedObjects = list?.sortedByDescending {
            dateFormat.parse(it.createdAt)?.time
        }

        return sortedObjects
    }


}
