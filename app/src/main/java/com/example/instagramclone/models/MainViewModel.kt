package com.example.instagramclone.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagramclone.ListStoryItem
import com.example.instagramclone.StoryResponse
import com.example.instagramclone.activities.ProfileActivity
import com.example.instagramclone.network.ApiConfig
import com.example.instagramclone.network.responses.CommunityDetail
import com.example.instagramclone.network.responses.CommunityDetailResponse
import com.example.instagramclone.network.responses.CommunityItem
import com.example.instagramclone.network.responses.CommunityResponse
import com.example.instagramclone.network.responses.ProfileResponse
import com.example.instagramclone.network.responses.RecipeResponse
import com.example.instagramclone.network.responses.ResultsItem
import com.example.instagramclone.network.responses.UserProfile
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
    
    private val _detailPostData = MutableLiveData<CommunityDetail>()
    val detailPostData: LiveData<CommunityDetail> = _detailPostData
    
    private val _recipeData = MutableLiveData<List<ResultsItem>>()
    val recipeData : LiveData<List<ResultsItem>> = _recipeData
    
    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty : LiveData<Boolean> = _isEmpty

    private val _userData = MutableLiveData<UserProfile>()
    val userData : LiveData<UserProfile> = _userData
    

//    val stories: LiveData<PagingData<ListStoryItem>> = repository.getStories().cachedIn(viewModelScope)

    companion object{
        private const val TAG = "MainViewModel"
    }

    fun getUserInfo(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(token)
        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userData.value = response.body()?.data?.user!!
                } else {
                    Log.e(ProfileActivity.TAG, "Unsuccessful response: ${response}")

                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun searchRecipe(token: String, query: String, limit: Int) {
        _isLoading.value = true
        _isEmpty.value = false
        val client = ApiConfig.getApiService().searchRecipes("Bearer $token",query,limit)
        client.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {

                    if (response.body()?.data?.results?.isEmpty() == true) {
                        _isEmpty.value = true
                    }

                    Log.d(TAG, "query : $query")
                    Log.d(TAG, "RECIPE DATA: ${response.body()}")

                    Log.d(TAG, "RECIPE DATA: ${response.body()?.data?.results}")
                    Log.d(TAG, "Empty? : ${response.body()?.data?.results?.isEmpty()}")
                    _recipeData.value = (response.body()?.data?.results as List<ResultsItem>?)!!
                } else {
                    Log.e(TAG, "Unsuccessful response: ${response}")

                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

    }

    fun getDetailPost(token: String, id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailPost("Bearer $token",id)
        client.enqueue(object : Callback<CommunityDetailResponse> {
            override fun onResponse(
                call: Call<CommunityDetailResponse>,
                response: Response<CommunityDetailResponse>
            ) {

                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseData = response.body()?.data?.community
                    Log.d(TAG, "RESPONSE: $responseData")
                    _detailPostData.value = responseData!!

//                    if (responseData is CommunityDetail) {
//                        // Handle single community object
//                        val community = responseData
//                        Log.d(TAG, "DATA: ${community}")
//                        // ... continue processing
//                    } else if (responseData is List<*>) {
//                        // Handle list of communities
//                        val communityList = responseData as List<CommunityDetail>
//                        Log.d(TAG, "DATALIST: ${communityList}")
//
//                        // ... continue processing
//                    }
                } else {
                    Log.e(TAG, "Unsuccessful response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CommunityDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
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
//                    Log.d(TAG, "onResponse: ${response.body()?.data}")
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

