package com.boedayaid.boedayaapp.ui.home.bottomsheet

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.api.ApiConfig
import com.boedayaid.boedayaapp.data.model.Suku
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BottomSheetViewModel : ViewModel() {

    private val api = ApiConfig.provideApiService()

    private val _listSuku = MutableLiveData<List<Suku>>()
    val listSuku get() = _listSuku

    fun getSukuFromServer(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultSuku = api.getSukuByProvince(id)
                val result = resultSuku.suku.map { sukuItem ->
                    Suku(sukuItem.id, sukuItem.sukuName, sukuItem.sukuImage)
                }
                _listSuku.postValue(result)
            } catch (e: Exception) {
                Log.e("Network Exception", e.message.toString())
            }
        }
    }
}