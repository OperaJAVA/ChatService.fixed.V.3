package ru.netology

fun main() {

    val service = ChatService()
    val userId = 456

    service.addMessage(listOf(userId, 123), Message(text = "message1"))
    service.addMessage(listOf(userId, 123), Message(text = "message4"))
    service.addMessage(listOf(userId, 111), Message(text = "message2"))
    service.addMessage(listOf(userId, 111), Message(text = "message5"))
    service.addMessage(listOf(userId, 111), Message(text = "message6"))
    service.addMessage(listOf(122, 455), Message(text = "message3"))
    println(service)

    println("Все чаты пользователя $userId: " + service.getChats(userId).toString())
    println("Все чаты пользователя 789: " + service.getChats(789).toString())

    val chatIds = listOf(setOf(123))
    val lastMessages = service.getLastMessages(chatIds)
    println("Последние сообщения из чатов $chatIds: $lastMessages")

    service.readMessagesFromChatById(setOf(userId), 2)

    val posts = listOf(
        Post(6, PostType.PHOTO, "...", 2),
        Post(5, PostType.VIDEO, "...", 30),
        Post(4, PostType.PHOTO, "...", 2),
        Post(3, PostType.AUDIO, "...", 5),
        Post(2, PostType.VIDEO, "...", 60),
        Post(1, PostType.AUDIO, "...", 1)

    )
    val topBoundary = 10
    val topCount = 5
    val emptyDigestContent = "Digest is empty"

    val topDigest = posts.asSequence()
        .filter { it.likes >= topBoundary }
        .take(topCount)
        .map { it.content }
        .joinToString(separator = "\n")
        .ifEmpty { emptyDigestContent }
        .let { Post(0, PostType.DIGEST, it) }

    println(topDigest)
}