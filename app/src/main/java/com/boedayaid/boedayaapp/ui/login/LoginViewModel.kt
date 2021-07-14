package com.boedayaid.boedayaapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.firebase.auth.AuthResult
import com.boedayaid.boedayaapp.data.firebase.auth.FirebaseAuthService
import com.boedayaid.boedayaapp.data.firebase.firestore.FirebaseFirestoreService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _authState = MutableLiveData<AuthResult>()
    val authState get() = _authState

    fun login(email:String, password:String){
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseAuthService.login(email,password)
                .collect {
                    _authState.postValue(it)
                }
        }
    }
}