package com.boedayaid.boedayaapp.ui.home

import androidx.lifecycle.*
import com.boedayaid.boedayaapp.data.model.Island
import com.boedayaid.boedayaapp.data.model.Province
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    init {
        getListIslandFromServer()
    }

    companion object {
        const val DEFAULT_QUERY = "all"
    }

    val selectedIsland = MutableLiveData(DEFAULT_QUERY)

    private val _listIsland = MutableLiveData<List<Island>>()
    val listIsland get() = _listIsland

    val listProvince = selectedIsland.switchMap { queryString ->
        getListProvince(queryString)
    }

    private fun getListIslandFromServer() {
        viewModelScope.launch(Dispatchers.IO) {
            val resultIsland = listOf(
                Island(0, "Sumatra"),
                Island(1, "Jawa"),
                Island(2, "Kalimantan"),
                Island(3, "Sulawesi"),
                Island(4, "NTT"),
                Island(5, "NTB"),
                Island(6, "Papua"),
            )
            _listIsland.postValue(resultIsland)
        }
    }

    private fun getListProvince(query: String): LiveData<List<Province>> {
        val provinces = MutableLiveData<List<Province>>()
        val result = listOf(
            Province(
                0,
                "Jawa Timur",
                "https://4.bp.blogspot.com/-jCviGhuFayk/XEG2cL1eDdI/AAAAAAAABxY/qJIiRaUm_5gAecCkHUb97B9RQysI4xleACLcBGAs/s1600/logo%2Bprovinsi%2Bjawa%2Btimur.jpg"
            ),
            Province(
                1,
                "Jawa Barat",
                "https://4.bp.blogspot.com/-jCviGhuFayk/XEG2cL1eDdI/AAAAAAAABxY/qJIiRaUm_5gAecCkHUb97B9RQysI4xleACLcBGAs/s1600/logo%2Bprovinsi%2Bjawa%2Btimur.jpg"
            ),
            Province(
                2,
                "Jawa Tengah",
                "https://4.bp.blogspot.com/-jCviGhuFayk/XEG2cL1eDdI/AAAAAAAABxY/qJIiRaUm_5gAecCkHUb97B9RQysI4xleACLcBGAs/s1600/logo%2Bprovinsi%2Bjawa%2Btimur.jpg"
            ),
            Province(
                3,
                "Gorontalo",
                "https://4.bp.blogspot.com/-jCviGhuFayk/XEG2cL1eDdI/AAAAAAAABxY/qJIiRaUm_5gAecCkHUb97B9RQysI4xleACLcBGAs/s1600/logo%2Bprovinsi%2Bjawa%2Btimur.jpg"
            ),
            Province(
                4,
                "DKI Jakarta",
                "https://4.bp.blogspot.com/-jCviGhuFayk/XEG2cL1eDdI/AAAAAAAABxY/qJIiRaUm_5gAecCkHUb97B9RQysI4xleACLcBGAs/s1600/logo%2Bprovinsi%2Bjawa%2Btimur.jpg"
            ),
        )

        val resultJawa = listOf(

            Province(
                0,
                "Jawa",
                "https://2.bp.blogspot.com/-XdJB7P9iSSM/Wbi5ojujjwI/AAAAAAAAD_c/q7EVtXBLGIUvzCgMLXlS59eBvDU_KgenQCLcBGAs/s1600/Logo%2BProv%2BJawa%2BBarat.png"
            ),
            Province(
                1,
                "Jawa",
                "https://2.bp.blogspot.com/-XdJB7P9iSSM/Wbi5ojujjwI/AAAAAAAAD_c/q7EVtXBLGIUvzCgMLXlS59eBvDU_KgenQCLcBGAs/s1600/Logo%2BProv%2BJawa%2BBarat.png"
            ),
            Province(
                2,
                "Jawa",
                "https://2.bp.blogspot.com/-XdJB7P9iSSM/Wbi5ojujjwI/AAAAAAAAD_c/q7EVtXBLGIUvzCgMLXlS59eBvDU_KgenQCLcBGAs/s1600/Logo%2BProv%2BJawa%2BBarat.png"
            ),
            Province(
                3,
                "Jawa",
                "https://2.bp.blogspot.com/-XdJB7P9iSSM/Wbi5ojujjwI/AAAAAAAAD_c/q7EVtXBLGIUvzCgMLXlS59eBvDU_KgenQCLcBGAs/s1600/Logo%2BProv%2BJawa%2BBarat.png"
            ),
            Province(
                4,
                "Jawa",
                "https://2.bp.blogspot.com/-XdJB7P9iSSM/Wbi5ojujjwI/AAAAAAAAD_c/q7EVtXBLGIUvzCgMLXlS59eBvDU_KgenQCLcBGAs/s1600/Logo%2BProv%2BJawa%2BBarat.png"
            ),
            Province(
                5,
                "Jawa",
                "https://2.bp.blogspot.com/-XdJB7P9iSSM/Wbi5ojujjwI/AAAAAAAAD_c/q7EVtXBLGIUvzCgMLXlS59eBvDU_KgenQCLcBGAs/s1600/Logo%2BProv%2BJawa%2BBarat.png"
            ),
        )
        provinces.value = if (query == DEFAULT_QUERY) result else resultJawa
        return provinces
    }
}