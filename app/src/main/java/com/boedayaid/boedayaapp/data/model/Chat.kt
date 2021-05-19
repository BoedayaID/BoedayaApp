package com.boedayaid.boedayaapp.data.model

enum class ChatType {
    Regular,
    Aksara,
}

enum class ChatAddress{
    TO,
    FROM,
}

open class Chat(val id: Int, val text: String)

class ChatFrom(id: Int, text: String, val type: ChatType) : Chat(id, text)

class ChatTo(id: Int, text: String) : Chat(id, text)