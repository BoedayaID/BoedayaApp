package com.boedayaid.boedayaapp.data.api.respons

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("keyword")
	val keyword: String
)
