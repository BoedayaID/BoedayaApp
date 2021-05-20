package com.boedayaid.boedayaapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.api.ApiConfig
import com.boedayaid.boedayaapp.data.api.respons.ProvinceResponse
import com.boedayaid.boedayaapp.data.model.Island
import com.boedayaid.boedayaapp.data.model.Province
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val api = ApiConfig.provideApiService()

    init {
        getListIslandFromServer()
        getListProvince(DEFAULT_QUERY)
    }

    companion object {
        const val DEFAULT_QUERY = 0
    }

    private val _selectedIsland = MutableLiveData(DEFAULT_QUERY)
    val selectedIsland get() = _selectedIsland

    private val _listIsland = MutableLiveData<List<Island>>()
    val listIsland get() = _listIsland

    private val _listProvince = MutableLiveData<List<Province>>()
    val listProvince get() = _listProvince

    private fun getListIslandFromServer() {
        viewModelScope.launch(Dispatchers.IO) {
            val resultIsland = api.getAllIsland()

            val result = resultIsland.island.map { islandItem ->
                Island(islandItem.id, islandItem.name)
            }

            _listIsland.postValue(result)
        }
    }

    fun getListProvince(islandId: Int) {
        viewModelScope.launch {

            val resultProvince: ProvinceResponse = if (islandId == DEFAULT_QUERY) {
                api.getAllProvince()
            } else {
                api.getProvinceByIsland(islandId)
            }

            val result = resultProvince.province.map { provinceItem ->
                Province(
                    provinceItem.provinceId,
                    provinceItem.provinceName,
                    provinceItem.provinceImage
                )
            }
            _listProvince.postValue(result)
        }
    }
}