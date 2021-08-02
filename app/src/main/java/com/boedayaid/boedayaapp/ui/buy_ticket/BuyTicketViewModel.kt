package com.boedayaid.boedayaapp.ui.buy_ticket

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.firebase.firestore.FirebaseFirestoreService
import com.boedayaid.boedayaapp.data.model.DetailSuku
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BuyTicketViewModel : ViewModel() {

    fun buyTicket(uid: String, data: DetailSuku.TempatWisata, amount: Int, bitArray: ByteArray) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseFirestoreService.butTicket(uid, data, amount, bitArray)
        }
    }

}