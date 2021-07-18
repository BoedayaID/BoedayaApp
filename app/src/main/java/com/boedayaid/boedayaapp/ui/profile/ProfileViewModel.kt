package com.boedayaid.boedayaapp.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.firebase.auth.FirebaseAuthService
import com.boedayaid.boedayaapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _userProfile = MutableLiveData<User>()
    val userProfile get() = _userProfile

    fun getProfile(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseAuthService.getUserProfile(uid)
                .collect {
                    if (it != null) {
                        _userProfile.postValue(it)
                    }
                }
        }
    }

    fun changeImageProfile(byteArray: ByteArray, uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseAuthService.changeImageProfile(byteArray, uid)
        }
    }

    fun logout() {
        FirebaseAuthService.logout()
    }
}