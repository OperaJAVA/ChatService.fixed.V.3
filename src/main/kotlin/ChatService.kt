package ru.netology

// Класс, представляющий сервис работы с чатами
class ChatService {
    val chats = hashMapOf<Set<Int>, Chat>()
    val messageLimit = 10
    override fun toString(): String {
        var result = "Состояние чатов:\n"
        chats.onEach { (key, value) ->
            result += "$key $value \n"
        }
        return result
    }

    // Метод для добавления сообщения в чат
    fun addMessage(userIds: List<Int>, message: Message): Chat {
        return chats.getOrPut(userIds.toSet()) {
            Chat(null)
        }.apply {
            messages.add(message)
        }
    }

    // Метод для получения сообщений из чата по идентификатору пользователей
    fun getMessagesFromChat(userIds: Set<Int>): List<Message> {
        val chat = chats.filter { entry -> entry.key.containsAll(userIds) }.values.firstOrNull()
        chat?.messages?.onEach { message -> message.isRead = true }
        return chat?.messages ?: emptyList()

    }

    // Метод для пометки сообщений в чате как прочитанных
    fun readChat(userIds: List<Int>): Boolean {
        val chat = chats.filter { entry -> entry.key.containsAll(userIds) }.values.firstOrNull()
        chat?.readMessages()
        return true
    }

    // Метод для удаления чата по идентификатору пользователей
    fun deleteChat(userIds: List<Int>): Boolean {
        return chats.remove(userIds.toSet()) != null
    }

    // Метод для получения всех чатов пользователя
    fun getChats(userId: Int): List<Chat> {
        return chats.filter { entry -> entry.key.contains(userId) }.values.toList()
    }

    // Метод для получения чата по идентификатору пользователей
    fun getChat(userIds: List<Int>): List<Chat> {
        return chats.filter { entry -> entry.key.containsAll(userIds) }.values.toList()
    }

    // Метод для получения количества непрочитанных чатов у пользователя
    fun getUnreadChatsCount(userId: Int): Int {
        return chats.filter { entry -> entry.key.contains(userId) }.values.filter { !it.hasUnreadMessages() }.count()
    }

    // Метод для получения последних сообщений из чатов
    fun getLastMessages(chatIds: List<Set<Int>>): List<String> {
        return chatIds.flatMap { chatId ->
            getMessagesFromChat(chatId)
                .reversed()
                .take(messageLimit)
                .mapNotNull { message ->
                    message.text
                }
        }
    }

    // Вот реализация метода readMessagesFromChatById, которая повторяет функциональность метода getMessagesFromChat,
// но также вставляет вызов метода take:
// Метод для пометки сообщений в чате как прочитанных по идентификатору пользователя и количеству сообщений
    fun readMessagesFromChatById(userIds: Set<Int>, messageCount: Int): List<Message> {
        return chats.filter { entry -> entry.key.containsAll(userIds) } // Фильтруем чаты, чтобы оставить только те, которые содержат id всех пользователей из переданного множества
            .values // Оставляем только значения из отфильтрованной коллекции (отбрасываем ключи)
            .asSequence()
            .firstOrNull() // Если получившийся список пустой, то возвращаем null, если нет - берем только первый чат из него
            ?.messages // Взять сообщения из этого чата
            ?.takeLast(messageCount) // Взять messageCount с конца списка (если не хватит, то вернет сколько есть)
            ?.onEach { message ->
                message.isRead = true
            } // На каждом элементе списка выполнить действие message.isRead = true (пометить прочитанным), onEach возвращает тот же список, на котором вызывался
            ?: emptyList<Message>() // Если на каком-то шаге поймали null, то вернуть пустой список
                .toList()

    }
}