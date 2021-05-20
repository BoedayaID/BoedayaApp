package com.boedayaid.boedayaapp.data.api.respons

import com.google.gson.annotations.SerializedName

data class IslandResponse(

    @field:SerializedName("pulau")
    val island: List<IslandItem>
) {
    data class IslandItem(

        @field:SerializedName("nama")
        val name: String,

        @field:SerializedName("id")
        val id: Int
    )
}


