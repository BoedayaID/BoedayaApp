package com.boedayaid.boedayaapp.data.firebase.firestore

import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

object FirebaseFirestoreService {
    private val bucketFirestore = FirebaseFirestore.getInstance().collection("bucket")
    private val historyFirestore = FirebaseFirestore.getInstance().collection("history")

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
}