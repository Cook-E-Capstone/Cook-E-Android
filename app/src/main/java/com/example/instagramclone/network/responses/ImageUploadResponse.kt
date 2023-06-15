package com.example.instagramclone.network.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ImageUploadResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

@Parcelize
data class Nutrition(

	@field:SerializedName("sodium_mg")
	val sodiumMg: Int? = null,

	@field:SerializedName("serat_g")
	val seratG: Double? = null,

	@field:SerializedName("lemak_total_g")
	val lemakTotalG: Double? = null,

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
	val zatBesiMg: Double? = null,

	@field:SerializedName("gula_g")
	val gulaG: Double? = null,

	@field:SerializedName("vitamin_k")
	val vitaminK: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kalori_kkal")
	val kaloriKkal: Int? = null,

	@field:SerializedName("protein_g")
	val proteinG: Double? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null
) : Parcelable

data class Data(

	@field:SerializedName("nutrition")
	val nutrition: Nutrition? = null,

	@field:SerializedName("confidence")
	val confidence: Double? = null,

	@field:SerializedName("name")
	val name: String? = null
)
