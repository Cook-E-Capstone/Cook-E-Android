package com.example.instagramclone.network.responses

import com.google.gson.annotations.SerializedName

data class CommunityResponse(

	@field:SerializedName("data")
	val data: CommunityData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class CommunityData(

	@field:SerializedName("community")
	val community: List<CommunityItem?>? = null
)

data class CommunityItem(

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

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
