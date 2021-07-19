package com.boedayaid.boedayaapp.data.api.respons

import com.google.gson.annotations.SerializedName

class AksaraResponse(
    @field:SerializedName("prediction")
    val prediction: String
)