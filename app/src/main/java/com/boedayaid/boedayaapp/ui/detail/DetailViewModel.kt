package com.boedayaid.boedayaapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.api.ApiConfig
import com.boedayaid.boedayaapp.data.model.DetailSuku
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    val apiServices = ApiConfig.provideApiService()

    private val _detailSuku = MutableLiveData<DetailSuku>()
    val detailSuku get() = _detailSuku

    fun getDetailSuku(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = apiServices.getSukuById(id)

            val rumahAdat = result.rumahAdat?.map {
                DetailSuku.RumahAdat(
                    it?.id,
                    it?.sukuId,
                    it?.namaRumahAdat,
                    it?.gambar,
                    it?.deskripsi
                )
            }

            val kesenian = result.kesenian?.map {
                DetailSuku.Kesenian(
                    it?.id,
                    it?.sukuId,
                    it?.namaKesenian,
                    it?.deskripsi,
                    it?.gambar,
                    it?.kategoriKesenian
                )
            }

            val makanan = result.makanan?.map {
                DetailSuku.Makanan(
                    it?.id,
                    it?.sukuId,
                    it?.namaMakanan,
                    it?.gambar,
                    it?.deskripsi
                )
            }

            val tempatWisata = result.tempatWisata?.map {
                DetailSuku.TempatWisata(
                    it?.id,
                    it?.sukuId,
                    it?.namaTempat,
                    it?.gambar,
                    it?.alamat,
                    it?.deskripsi,
                    it?.mapUrl
                )
            }

            val detailSuku = DetailSuku(
                result.id,
                result.provinsiId,
                result.namaSuku,
                result.gambar,
                result.sejarah,
                result.bahasa,
                result.aksaraKuno,
                result.aksaraTranslate,
                rumahAdat,
                kesenian,
                makanan,
                tempatWisata
            )

            _detailSuku.postValue(detailSuku)
        }
    }
}