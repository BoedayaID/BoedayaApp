package com.boedayaid.boedayaapp.data.api.respons

import com.google.gson.annotations.SerializedName

data class SukuDetailResponse(

	@field:SerializedName("aksara_kuno")
	val aksaraKuno: String? = null,

	@field:SerializedName("aksara_translate")
	val aksaraTranslate: Int? = null,

	@field:SerializedName("nama_suku")
	val namaSuku: String? = null,

	@field:SerializedName("rumah_adat")
	val rumahAdat: List<RumahAdatItem?>? = null,

	@field:SerializedName("bahasa")
	val bahasa: String? = null,

	@field:SerializedName("provinsi_id")
	val provinsiId: Int? = null,

	@field:SerializedName("kesenian")
	val kesenian: List<KesenianItem?>? = null,

	@field:SerializedName("sejarah")
	val sejarah: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("tempat_wisata")
	val tempatWisata: List<TempatWisataItem?>? = null,

	@field:SerializedName("makanan")
	val makanan: List<MakananItem?>? = null
){

	data class KesenianItem(

		@field:SerializedName("suku_id")
		val sukuId: Int? = null,

		@field:SerializedName("kategori_kesenian")
		val kategoriKesenian: String? = null,

		@field:SerializedName("nama_kesenian")
		val namaKesenian: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("deskripsi")
		val deskripsi: String? = null,

		@field:SerializedName("gambar")
		val gambar: String? = null
	)

	data class TempatWisataItem(

		@field:SerializedName("suku_id")
		val sukuId: Int? = null,

		@field:SerializedName("map_url")
		val mapUrl: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("deskripsi")
		val deskripsi: String? = null,

		@field:SerializedName("nama_tempat")
		val namaTempat: String? = null,

		@field:SerializedName("gambar")
		val gambar: String? = null,

		@field:SerializedName("alamat")
		val alamat: String? = null
	)

	data class RumahAdatItem(

		@field:SerializedName("suku_id")
		val sukuId: Int? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("deskripsi")
		val deskripsi: String? = null,

		@field:SerializedName("nama_rumah_adat")
		val namaRumahAdat: String? = null,

		@field:SerializedName("gambar")
		val gambar: String? = null
	)

	data class MakananItem(

		@field:SerializedName("suku_id")
		val sukuId: Int? = null,

		@field:SerializedName("nama_makanan")
		val namaMakanan: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("deskripsi")
		val deskripsi: String? = null,

		@field:SerializedName("gambar")
		val gambar: String? = null
	)
}

