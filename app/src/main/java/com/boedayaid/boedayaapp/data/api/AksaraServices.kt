package com.boedayaid.boedayaapp.data.api

import com.boedayaid.boedayaapp.data.api.respons.AksaraResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AksaraServices {

    @POST("predict")
    suspend fun predictJawaImage(
        @Body image: RequestBody
    ): AksaraResponse

    @POST("predict-sunda")
    suspend fun predictSundaImage(
        @Body image: RequestBody
    ): AksaraResponse

}