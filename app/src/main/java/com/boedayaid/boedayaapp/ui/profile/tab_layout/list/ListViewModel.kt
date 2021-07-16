package com.boedayaid.boedayaapp.ui.profile.tab_layout.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.model.DetailSuku
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {
    private val _resultList = MutableLiveData<String>()
    val resultList get() = _resultList

    fun getBucketList(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _resultList.postValue("Bucket")
        }
    }

    fun getHistoryList(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _resultList.postValue("History")
        }
    }
}