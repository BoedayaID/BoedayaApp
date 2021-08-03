package com.boedayaid.boedayaapp.ui.profile.tab_layout.ticket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.firebase.firestore.FirebaseFirestoreService
import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.boedayaid.boedayaapp.data.model.Ticket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TicketViewModel : ViewModel() {
    private val _resultList = MutableLiveData<List<Ticket>>()
    val resultList get() = _resultList

    fun getTicketList(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseFirestoreService.getTicketList(uid)
                .collect {
                    _resultList.postValue(it)
                }
        }
    }
}