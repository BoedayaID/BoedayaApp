package com.boedayaid.boedayaapp.data.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseFirestoreService {
    private val bucketFirestore = FirebaseFirestore.getInstance().collection("bucket")
    private val historyFirestore = FirebaseFirestore.getInstance().collection("history")
}