package com.boedayaid.boedayaapp.data.firebase.auth

enum class AuthState {
    LOADING,DONE, ERROR
}

class AuthResult(val state: AuthState,val message: String)