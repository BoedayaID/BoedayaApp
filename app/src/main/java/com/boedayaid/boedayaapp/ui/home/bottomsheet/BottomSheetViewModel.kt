package com.boedayaid.boedayaapp.ui.home.bottomsheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.model.Suku
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BottomSheetViewModel : ViewModel() {
    private val _listSuku = MutableLiveData<List<Suku>>()
    val listSuku get() = _listSuku

    fun getSukuFromServer(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultSuku = listOf(
                Suku(
                    1,
                    "Sunda",
                    "https://2.bp.blogspot.com/-Lu0rA0U8uE0/WwkOTodFD1I/AAAAAAAANrc/go7sfbHC4hcXJydJ9O4vNDPh3Y3tEYk7QCLcBGAs/s1600/Kujang.jpg"
                ),
                Suku(
                    2,
                    "Jawa",
                    "https://2.bp.blogspot.com/-Lu0rA0U8uE0/WwkOTodFD1I/AAAAAAAANrc/go7sfbHC4hcXJydJ9O4vNDPh3Y3tEYk7QCLcBGAs/s1600/Kujang.jpg"
                ),
                Suku(
                    2,
                    "Betawi",
                    "https://2.bp.blogspot.com/-Lu0rA0U8uE0/WwkOTodFD1I/AAAAAAAANrc/go7sfbHC4hcXJydJ9O4vNDPh3Y3tEYk7QCLcBGAs/s1600/Kujang.jpg"
                ),
            )
            _listSuku.postValue(resultSuku)
        }
    }
}