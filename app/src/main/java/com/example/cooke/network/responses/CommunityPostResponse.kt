package com.example.cooke.network.responses

import com.google.gson.annotations.SerializedName

data class CommunityPostResponse(

    @field:SerializedName("data")
	val data: CommunityPostData? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("status")
	val status: Int? = null
)

data class CommunityPostData(

	@field:SerializedName("community")
	val community: Community? = null
)

data class Community(

    @field:SerializedName("createdAt")
	val createdAt: String? = null,

    @field:SerializedName("pathfile")
	val pathfile: String? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("title")
	val title: String? = null,

    @field:SerializedName("userID")
	val userID: String? = null,

    @field:SerializedName("user")
	val user: UserPost? = null,

    @field:SerializedName("content")
	val content: String? = null,

    @field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class UserPost(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
