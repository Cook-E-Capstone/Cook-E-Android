package com.example.instagramclone.network.responses

import com.google.gson.annotations.SerializedName

data class CommunityDetailResponse(

	@field:SerializedName("data")
	val data: DataDetail? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class UserDetail(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class CommunityDetail(

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
	val user: UserDetail? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DataDetail(

	@field:SerializedName("community")
	val community: CommunityDetail? = null
)
