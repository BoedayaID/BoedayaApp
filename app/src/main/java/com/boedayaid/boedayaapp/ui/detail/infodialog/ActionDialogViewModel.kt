package com.boedayaid.boedayaapp.ui.detail.infodialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.firebase.firestore.FirebaseFirestoreService
import com.boedayaid.boedayaapp.data.model.DetailSuku
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ActionDialogViewModel : ViewModel() {
    private val _isOnBucket = MutableLiveData<Boolean>()
    val isOnBucket get() = _isOnBucket

    private val _isOnHistory = MutableLiveData<Boolean>()
    val isOnHistory get() = _isOnHistory

    fun checkOnBucket(uid: String, data: DetailSuku.TempatWisata) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseFirestoreService.checkOnBucketList(uid, data)
                .collect {
                    _isOnBucket.postValue(it)
                }
        }
    }

    fun checkOnHistory(uid: String, data: DetailSuku.TempatWisata) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseFirestoreService.checkOnHistoryList(uid, data)
                .collect {
                    _isOnHistory.postValue(it)
                }
        }
    }

    fun setOnBucket(uid: String, data: DetailSuku.TempatWisata) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseFirestoreService.setOnBucketList(uid, data)
        }
    }

    fun setOnHistory(uid: String, data: DetailSuku.TempatWisata) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseFirestoreService.setOnHistoryList(uid, data)
        }
    }

    fun deleteOnBucket(uid: String, data: DetailSuku.TempatWisata) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseFirestoreService.deleteOnBucketList(uid, data)
                .collect {
                    if (isOnBucket.value == true && it) {
                        _isOnBucket.postValue(false)
                    }
                }
        }
    }

    fun deleteOnHistory(uid: String, data: DetailSuku.TempatWisata) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseFirestoreService.deleteOnHistoryList(uid, data)
                .collect {
                    if (isOnHistory.value == true && it) {
                        _isOnHistory.postValue(false)
                    }
                }
        }
    }

}