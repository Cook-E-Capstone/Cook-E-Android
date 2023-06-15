package com.example.instagramclone.network.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RecipeResponse(

	@field:SerializedName("data")
	val data: RecipeData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

@Parcelize
data class ResultsItem(

	@field:SerializedName("langkah")
	val langkah: List<String?>? = null,

	@field:SerializedName("serat_g")
	val seratG: Double? = null,

	@field:SerializedName("kolesterol_mg")
	val kolesterolMg: Int? = null,

	@field:SerializedName("bahan")
	val bahan: List<String?>? = null,

	@field:SerializedName("kalori_kkal")
	val kaloriKkal: Int? = null,

	@field:SerializedName("karbohidrat_g")
	val karbohidratG: Double? = null,

	@field:SerializedName("protein_g")
	val proteinG: Double? = null,

	@field:SerializedName("gambar_url")
	val gambarUrl: String? = null,

	@field:SerializedName("hasil")
	val hasil: String? = null,

	@field:SerializedName("menu")
	val menu: String? = null,

	@field:SerializedName("lemak_g")
	val lemakG: Double? = null
) : Parcelable

data class RecipeData(

	@field:SerializedName("query")
	val query: String? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)
