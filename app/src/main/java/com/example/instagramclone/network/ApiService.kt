package com.example.instagramclone.network

import com.example.instagramclone.StoryResponse
import com.example.instagramclone.network.responses.CommunityResponse
import com.example.instagramclone.network.responses.CookeLoginResponse
import com.example.instagramclone.network.responses.CookeRegisterResponse
import com.example.instagramclone.network.responses.FileUploadResponse
import com.example.instagramclone.network.responses.ImageUploadResponse
import com.example.instagramclone.network.responses.RegisterResponse
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

    @Multipart
    @POST("ml/image")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
    ): Call<ImageUploadResponse>

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


//    @Multipart
//    @POST("ml/image")
//    fun scanImage(
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//    ) : Call<ImageUploadResponse>


}