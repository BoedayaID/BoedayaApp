package com.boedayaid.boedayaapp.data.firebase.firestore

import android.util.Log
import com.boedayaid.boedayaapp.data.firebase.auth.FirebaseAuthService
import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.boedayaid.boedayaapp.data.model.Ticket
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

object FirebaseFirestoreService {
    private val bucketFirestore = FirebaseFirestore.getInstance().collection("bucket")
    private val historyFirestore = FirebaseFirestore.getInstance().collection("history")
    private val ticketFirestore = FirebaseFirestore.getInstance().collection("ticket")
    private val userStorage = Firebase.storage

    fun getBucketList(uid: String): Flow<List<DetailSuku.TempatWisata>> =
        flow {
            try {
                val listWisata = mutableListOf<DetailSuku.TempatWisata>()
                val result = bucketFirestore.whereEqualTo("uid", uid).get().await()
                result.documents.forEach {
                    val data = DetailSuku.TempatWisata(
                        it["wisata_id"].toString().toInt(),
                        it["suku_id"].toString().toInt(),
                        it["wisata_nama"].toString(),
                        it["wisata_gambar"].toString(),
                        it["wisata_alamat"].toString(),
                        it["wisata_deskripsi"].toString(),
                        it["wisata_map_url"].toString(),
                    )
                    listWisata.add(data)
                }
                emit(listWisata)
            } catch (e: Exception) {
                emit(emptyList<DetailSuku.TempatWisata>())
            }
        }

    fun getHistoryList(uid: String): Flow<List<DetailSuku.TempatWisata>> =
        flow {
            try {
                val listWisata = mutableListOf<DetailSuku.TempatWisata>()
                val result = historyFirestore.whereEqualTo("uid", uid).get().await()
                result.documents.forEach {
                    val data = DetailSuku.TempatWisata(
                        it["wisata_id"].toString().toInt(),
                        it["suku_id"].toString().toInt(),
                        it["wisata_nama"].toString(),
                        it["wisata_gambar"].toString(),
                        it["wisata_alamat"].toString(),
                        it["wisata_deskripsi"].toString(),
                        it["wisata_map_url"].toString(),
                    )
                    listWisata.add(data)
                }
                emit(listWisata)
            } catch (e: Exception) {
                emit(emptyList<DetailSuku.TempatWisata>())
            }
        }

    fun getTicketList(uid: String): Flow<List<Ticket>> =
        flow {
            try {
                val listTicket = mutableListOf<Ticket>()
                val result = ticketFirestore.whereEqualTo("uid", uid).get().await()
                result.documents.forEach {
                    val tempatWisata = DetailSuku.TempatWisata(
                        it["wisata_id"].toString().toInt(),
                        it["suku_id"].toString().toInt(),
                        it["wisata_nama"].toString(),
                        it["wisata_gambar"].toString(),
                        it["wisata_alamat"].toString(),
                        it["wisata_deskripsi"].toString(),
                        it["wisata_map_url"].toString(),
                    )

                    val data = Ticket(
                        it.id,
                        it["amount"].toString().toInt(),
                        it["total"].toString().toInt(),
                        tempatWisata,
                        it["date_purchase"].toString().toLong(),
                        it["date_expired"].toString().toLong(),
                        it["transaction_prove"].toString(),
                    )
                    listTicket.add(data)
                }
                emit(listTicket)
            } catch (e: Exception) {
                emit(emptyList<Ticket>())
            }
        }

    fun setOnBucketList(uid: String, wisata: DetailSuku.TempatWisata) {
        val data = hashMapOf(
            "uid" to uid,
            "wisata_id" to wisata.id,
            "suku_id" to wisata.sukuId,
            "wisata_nama" to wisata.namaTempat,
            "wisata_gambar" to wisata.gambar,
            "wisata_alamat" to wisata.alamat,
            "wisata_deskripsi" to wisata.deskripsi,
            "wisata_map_url" to wisata.mapUrl,
        )
        bucketFirestore.document().set(data)
    }

    fun setOnHistoryList(uid: String, wisata: DetailSuku.TempatWisata) {
        val data = hashMapOf(
            "uid" to uid,
            "wisata_id" to wisata.id,
            "suku_id" to wisata.sukuId,
            "wisata_nama" to wisata.namaTempat,
            "wisata_gambar" to wisata.gambar,
            "wisata_alamat" to wisata.alamat,
            "wisata_deskripsi" to wisata.deskripsi,
            "wisata_map_url" to wisata.mapUrl,
        )
        historyFirestore.document().set(data)
    }

    fun butTicket(uid: String, wisata: DetailSuku.TempatWisata, amount: Int, byteArray: ByteArray) {
        val name = System.currentTimeMillis().toString()
        val storageRef = userStorage.reference
        val imageRef = storageRef.child(name)

        val uploadTask = imageRef.putBytes(byteArray)
        uploadTask.addOnFailureListener {
            Log.d("TAGS", it.message.toString())
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                imageRef.downloadUrl.addOnCompleteListener {
                    setTicket(uid, wisata, amount, name)
                }
            }
        }
    }

    private fun setTicket(
        uid: String,
        wisata: DetailSuku.TempatWisata,
        amount: Int,
        image: String
    ) {
        val total = amount * 25000
        val datePurchase = System.currentTimeMillis()
        val dateExpired = datePurchase + 604800000
        val data = hashMapOf(
            "uid" to uid,
            "wisata_id" to wisata.id,
            "suku_id" to wisata.sukuId,
            "wisata_nama" to wisata.namaTempat,
            "wisata_gambar" to wisata.gambar,
            "wisata_alamat" to wisata.alamat,
            "wisata_deskripsi" to wisata.deskripsi,
            "wisata_map_url" to wisata.mapUrl,
            "amount" to amount,
            "total" to total,
            "date_purchase" to datePurchase,
            "date_expired" to dateExpired,
            "transaction_prove" to image,
        )
        ticketFirestore.document().set(data)
    }

    fun deleteOnBucketList(uid: String, data: DetailSuku.TempatWisata): Flow<Boolean> =
        flow {
            try {
                val result = bucketFirestore.whereEqualTo("wisata_id", data.id)
                    .whereEqualTo("uid", uid)
                    .get().await()
                val id = result.documents[0].id
                bucketFirestore.document(id).delete().await()
                emit(true)
            } catch (e: Exception) {
                emit(false)
            }
        }

    fun deleteOnHistoryList(uid: String, data: DetailSuku.TempatWisata): Flow<Boolean> =
        flow {
            try {
                val result = historyFirestore.whereEqualTo("wisata_id", data.id)
                    .whereEqualTo("uid", uid)
                    .get().await()
                val id = result.documents[0].id
                historyFirestore.document(id).delete().await()
                emit(true)
            } catch (e: Exception) {
                emit(false)
            }
        }

    fun checkOnBucketList(uid: String, data: DetailSuku.TempatWisata): Flow<Boolean> =
        flow {
            try {
                val result = bucketFirestore.whereEqualTo("wisata_id", data.id)
                    .whereEqualTo("uid", uid)
                    .get().await()
                emit(!result.isEmpty)
            } catch (e: Exception) {
                emit(false)
            }
        }


    fun checkOnHistoryList(uid: String, data: DetailSuku.TempatWisata): Flow<Boolean> =
        flow {
            try {
                val result = historyFirestore.whereEqualTo("wisata_id", data.id)
                    .whereEqualTo("uid", uid)
                    .get().await()
                emit(!result.isEmpty)
            } catch (e: Exception) {
                emit(false)
            }
        }

//    fun changeImageProfile(byteArray: ByteArray, uid: String) {
//
//        val name = System.currentTimeMillis().toString()
//        val storageRef = FirebaseAuthService.userStorage.reference
//        val imageRef = storageRef.child(name)
//
//        val uploadTask = imageRef.putBytes(byteArray)
//        uploadTask.addOnFailureListener {
//            Log.d("TAGS", it.message.toString())
//        }.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                imageRef.downloadUrl.addOnCompleteListener {
//                    val data = hashMapOf(
//                        "imageProfile" to name
//                    )
//                    FirebaseAuthService.userFirestore.document(uid).set(
//                        data, SetOptions.mergeFields("imageProfile")
//                    )
//                }
//            }
//        }
//    }
}