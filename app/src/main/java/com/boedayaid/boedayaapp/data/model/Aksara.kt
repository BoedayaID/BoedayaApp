package com.boedayaid.boedayaapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Aksara(
    val nama: String,
    val symbol: Int,
) : Parcelable