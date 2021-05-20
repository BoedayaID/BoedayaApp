package com.boedayaid.boedayaapp.data.api.respons

import com.google.gson.annotations.SerializedName

data class ProvinceResponse(

    @field:SerializedName("provinsi")
    val province: List<ProvinceItem>
) {

    data class ProvinceItem(

        @field:SerializedName("nama_provinsi")
        val provinceName: String,

        @field:SerializedName("pulau_id")
        val isLandId: Int,

        @field:SerializedName("id")
        val provinceId: Int,

        @field:SerializedName("gambar")
        val provinceImage: String
    )
}
