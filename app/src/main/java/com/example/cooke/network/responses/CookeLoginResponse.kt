package com.example.cooke.network.responses

import com.google.gson.annotations.SerializedName

data class CookeLoginResponse(

    @field:SerializedName("data")
	val data: DataLogin? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("status")
	val status: Int? = null
)

data class User(

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

data class DataLogin(

    @field:SerializedName("user")
	val user: User? = null,

    @field:SerializedName("token")
	val token: String? = null
)
