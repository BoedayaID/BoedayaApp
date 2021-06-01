package com.boedayaid.boedayaapp.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    fun provideApiService(): ApiServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://stunning-net-313507.uc.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
        return retrofit.create(ApiServices::class.java)
    }

    fun provideAiService(): AiServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://b-audio-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
        return retrofit.create(AiServices::class.java)
    }

    fun provideTranslateService(): TranslateServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-translate-api.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
        return retrofit.create(TranslateServices::class.java)
    }
}