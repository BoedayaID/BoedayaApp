package com.boedayaid.boedayaapp.data.api

import com.boedayaid.boedayaapp.data.api.respons.AksaraResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AksaraServices {

    @POST("predict")
    suspend fun predictImage(
        @Body image: RequestBody
    ): AksaraResponse

}