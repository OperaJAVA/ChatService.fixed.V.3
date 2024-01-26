package ru.netology

enum class PostType {
    AUDIO, PHOTO, VIDEO, DIGEST,

}

data class Post(
    val id: Long,
    val type: PostType,
    val content: String,
    val likes: Int = 0
)


