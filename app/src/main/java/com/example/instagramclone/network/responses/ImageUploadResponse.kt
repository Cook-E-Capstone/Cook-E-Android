package com.example.instagramclone.network.responses

import com.google.gson.annotations.SerializedName

data class ImageUploadResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Nutrition(

	@field:SerializedName("sodium_mg")
	val sodiumMg: Int? = null,

	@field:SerializedName("serat_g")
	val seratG: Any? = null,

	@field:SerializedName("lemak_total_g")
	val lemakTotalG: Any? = null,

	@field:SerializedName("vitamin_a")
	val vitaminA: Int? = null,

	@field:SerializedName("vitamin_b_kompleks")
	val vitaminBKompleks: Int? = null,

	@field:SerializedName("vitamin_c")
	val vitaminC: Int? = null,

	@field:SerializedName("gambar_url")
	val gambarUrl: String? = null,

	@field:SerializedName("potasium_mg")
	val potasiumMg: Int? = null,

	@field:SerializedName("vitamin_e")
	val vitaminE: Int? = null,

	@field:SerializedName("kalsium_mg")
	val kalsiumMg: Int? = null,

	@field:SerializedName("zat_besi_mg")
	val zatBesiMg: Any? = null,

	@field:SerializedName("gula_g")
	val gulaG: Any? = null,

	@field:SerializedName("vitamin_k")
	val vitaminK: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kalori_kkal")
	val kaloriKkal: Int? = null,

	@field:SerializedName("protein_g")
	val proteinG: Any? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null
)

data class Data(

	@field:SerializedName("nutrition")
	val nutrition: Nutrition? = null,

	@field:SerializedName("confidence")
	val confidence: Any? = null,

	@field:SerializedName("name")
	val name: String? = null
)
