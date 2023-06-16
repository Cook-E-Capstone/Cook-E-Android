package com.example.cooke.network

import com.example.cooke.StoryResponse
import com.example.cooke.network.responses.CommunityDetailResponse
import com.example.cooke.network.responses.CommunityPostResponse
import com.example.cooke.network.responses.CommunityResponse
import com.example.cooke.network.responses.CookeLoginResponse
import com.example.cooke.network.responses.CookeRegisterResponse
import com.example.cooke.network.responses.ImageUploadResponse
import com.example.cooke.network.responses.ProfileResponse
import com.example.cooke.network.responses.RecipeResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String,
        @Query("location") location: Int? = 1
    ) : Call<StoryResponse>

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int? = 1
    ) : StoryResponse

    @GET("community/{id}")
    fun getDetailPost(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Call<CommunityDetailResponse>

//    @FormUrlEncoded
//    @POST("login")
//    fun login(
//        @Field("email") email: String,
//        @Field("password") password: String,
//    ): Call<LoginResponse>
//
//    @FormUrlEncoded
//    @POST("register")
//    fun register(
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String,
//    ): Call<RegisterResponse>

    @GET("user/profile")
    fun getUser(
        @Header("Authorization") token: String,
    ) : Call<ProfileResponse>

    @Multipart
    @POST("ml/image")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
    ): Call<ImageUploadResponse>

    @GET("ml/recipe")
    fun searchRecipes(
        @Header("Authorization") token: String,
        @Query("query") query: String,
        @Query("limit") limit: Int
    ): Call<RecipeResponse>


    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<CookeLoginResponse>

    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String,
    ): Call<CookeRegisterResponse>

    @GET("community")
    fun getAllPosts(
        @Header("Authorization") token: String,
    ) : Call<CommunityResponse>

    @Multipart
    @POST("community")
    fun postCommunity(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,

        ): Call<CommunityPostResponse>





//    @Multipart
//    @POST("ml/image")
//    fun scanImage(
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//    ) : Call<ImageUploadResponse>


}