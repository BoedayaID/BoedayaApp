package com.boedayaid.boedayaapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailSuku(


    val id: Int? = null,
    val provinsiId: Int? = null,
    val namaSuku: String? = null,
    val gambar: String? = null,
    val sejarah: String? = null,
    val bahasa: String? = null,
    val aksaraKuno: String? = null,
    val aksaraTranslate: Int? = null,
    val rumahAdat: List<RumahAdat?>? = null,
    val kesenian: List<Kesenian?>? = null,
    val makanan: List<Makanan?>? = null,
    val tempatWisata: List<TempatWisata?>? = null,
) : Parcelable {
    @Parcelize
    data class Kesenian(
        val id: Int? = null,
        val sukuId: Int? = null,
        val namaKesenian: String? = null,
        val deskripsi: String? = null,
        val gambar: String? = null,
        val kategoriKesenian: String? = null,
    ) : Parcelable

    @Parcelize
    data class TempatWisata(
        val id: Int? = null,
        val sukuId: Int? = null,
        val namaTempat: String? = null,
        val gambar: String? = null,
        val alamat: String? = null,
        val deskripsi: String? = null,
        val mapUrl: String? = null,

        ) : Parcelable

    @Parcelize
    data class RumahAdat(
        val id: Int? = null,
        val sukuId: Int? = null,
        val namaRumahAdat: String? = null,
        val gambar: String? = null,
        val deskripsi: String? = null,
    ) : Parcelable

    @Parcelize
    data class Makanan(
        val id: Int? = null,
        val sukuId: Int? = null,
        val namaMakanan: String? = null,
        val gambar: String? = null,
        val deskripsi: String? = null,
    ) : Parcelable
}

