package com.boedayaid.boedayaapp.data.model

data class Ticket(
    val id: String,
    val amount: Int,
    val total: Int,
    val tempatWisata: DetailSuku.TempatWisata,
    val datePurchase: Long,
    val dateExpired: Long,
    val transactionProve: String,
)