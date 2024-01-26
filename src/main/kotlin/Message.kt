package ru.netology

// Класс, представляющий сообщение
data class Message(
    var isRead: Boolean = false,
    val text: String
)
