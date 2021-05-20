package com.boedayaid.boedayaapp.data.api

import com.boedayaid.boedayaapp.data.api.respons.IslandResponse
import com.boedayaid.boedayaapp.data.api.respons.ProvinceResponse
import com.boedayaid.boedayaapp.data.api.respons.SukuResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("api/pulau")
    suspend fun getAllIsland(): IslandResponse

    @GET("api/provinsi")
    suspend fun getAllProvince(): ProvinceResponse

    @GET("api/pulau/{id}/provinsi")
    suspend fun getProvinceByIsland(@Path("id") id: Int): ProvinceResponse

    @GET("api/provinsi/{id}/suku")
    suspend fun getSukuByProvince(@Path("id") id: Int): SukuResponse
}