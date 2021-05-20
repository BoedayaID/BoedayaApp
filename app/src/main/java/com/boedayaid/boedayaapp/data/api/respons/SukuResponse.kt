package com.boedayaid.boedayaapp.data.api.respons

import com.google.gson.annotations.SerializedName

data class SukuResponse(

	@field:SerializedName("suku")
	val suku: List<SukuItem>
){
	data class SukuItem(

		@field:SerializedName("nama_suku")
		val sukuName: String,

		@field:SerializedName("id")
		val id: Int,

		@field:SerializedName("gambar")
		val sukuImage: String
	)
}
