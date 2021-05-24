package com.boedayaid.boedayaapp.data.api

import com.boedayaid.boedayaapp.data.api.respons.TranslateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateServices {

    @GET("translate")
    suspend fun translate(
        @Query("text") text: String,
        @Query("from") from: String = "id",
        @Query("to") to: String = "en",
    ): TranslateResponse
}