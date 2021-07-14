package com.boedayaid.boedayaapp.data.firebase.auth

import com.boedayaid.boedayaapp.data.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

object FirebaseAuthService {
    private val auth = Firebase.auth
    private val userFirestore = FirebaseFirestore.getInstance().collection("users")

    fun register(username: String, email: String, password: String): Flow<AuthResult> =
        flow {
            try {
                emit(AuthResult(AuthState.LOADING, "Sedang membuat akun !"))
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val data = mapOf(
                    "id" to result.user?.uid,
                    "username" to username,
                    "email" to email,
                    "imageProfile" to ""
                )
                userFirestore.document(result.user?.uid.toString()).set(data).await()
                emit(AuthResult(AuthState.DONE, "Akun berhasil dibuat ! Selamat datang."))
            } catch (e: Exception) {
                emit(AuthResult(AuthState.ERROR, e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun login(email: String, password: String): Flow<AuthResult> =
        flow {
            try {
                emit(AuthResult(AuthState.LOADING, "Mencoba login!"))
                auth.signInWithEmailAndPassword(email, password).await()
                emit(AuthResult(AuthState.DONE, "Akun berhasil dibuat ! Selamat datang."))
            } catch (e: Exception) {
                emit(AuthResult(AuthState.ERROR, e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun logout() {
        auth.signOut()
    }

    fun getUserProfile(uid: String): Flow<User?> =
        flow {
            try {
                val result = userFirestore.document(uid).get().await()
                val user = User(
                    result.data?.get("id") as String,
                    result.data?.get("username") as String,
                    result.data?.get("email") as String,
                    result.data?.get("imageProfile") as String,
                )
                emit(user)
            } catch (e: Exception) {
                emit(null)
            }
        }.flowOn(Dispatchers.IO)
}