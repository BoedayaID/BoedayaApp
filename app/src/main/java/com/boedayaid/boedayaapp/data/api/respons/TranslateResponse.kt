package com.boedayaid.boedayaapp.data.api.respons

import com.google.gson.annotations.SerializedName

data class TranslateResponse(

	@field:SerializedName("result")
	val result: String,

	@field:SerializedName("origin")
	val origin: String
)
