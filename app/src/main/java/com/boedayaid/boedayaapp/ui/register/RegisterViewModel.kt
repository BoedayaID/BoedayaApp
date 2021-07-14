package com.boedayaid.boedayaapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.firebase.auth.AuthResult
import com.boedayaid.boedayaapp.data.firebase.auth.FirebaseAuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _authState = MutableLiveData<AuthResult>()
    val authState get() = _authState

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseAuthService.register(username, email, password)
                .collect {
                    _authState.postValue(it)
                }
        }
    }

}