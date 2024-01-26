package ru.netology

// Класс, представляющий чат
data class Chat(val userId: Int?) {
    val messages = mutableListOf<Message>()

    // Метод для пометки всех сообщений как прочитанных
    fun readMessages() {
        for (message in messages) {
            message.isRead = true
        }
    }

    // Метод для проверки наличия непрочитанных сообщений
    fun hasUnreadMessages(): Boolean {
        return messages.filter { !it.isRead }.isEmpty()
    }

    override fun toString(): String {
        var result = "[ Chat "
        messages.onEach { message ->
            result += "$message"
        }
        return result + "]"
    }
}