package com.boedayaid.boedayaapp.data.firebase.auth

import android.util.Log
import com.boedayaid.boedayaapp.data.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

object FirebaseAuthService {
    private val auth = Firebase.auth
    private val userFirestore = FirebaseFirestore.getInstance().collection("users")
    private val userStorage = Firebase.storage


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

    fun changeImageProfile(byteArray: ByteArray, uid: String) {
        val name = System.currentTimeMillis().toString()
        val storageRef = userStorage.reference
        val imageRef = storageRef.child(name)

        val uploadTask = imageRef.putBytes(byteArray)
        uploadTask.addOnFailureListener {
            Log.d("TAGS", it.message.toString())
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                imageRef.downloadUrl.addOnCompleteListener {
                    val data = hashMapOf(
                        "imageProfile" to name
                    )
                    userFirestore.document(uid).set(
                        data, SetOptions.mergeFields("imageProfile")
                    )
                }
            }
        }
    }
}