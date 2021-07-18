package com.boedayaid.boedayaapp.ui.profile.tab_layout.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.firebase.firestore.FirebaseFirestoreService
import com.boedayaid.boedayaapp.data.model.DetailSuku
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {
    private val _resultList = MutableLiveData<List<DetailSuku.TempatWisata>>()
    val resultList get() = _resultList

    fun getBucketList(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseFirestoreService.getBucketList(uid)
                .collect {
                    _resultList.postValue(it)
                }
        }
    }

    fun getHistoryList(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseFirestoreService.getHistoryList(uid)
                .collect {
                    _resultList.postValue(it)
                }
        }
    }
}