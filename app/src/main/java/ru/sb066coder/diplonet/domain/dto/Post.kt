package ru.sb066coder.diplonet.domain.dto

data class Post(
    val authorId: Int,
    val author: String,
    val authorAvatar: String? = null,
    val authorJob: String? = null,
    val content: String,
    val published: String,
    val coords: Coordinates? = null,
    val link: String? = null,
    val likeOwnerIds: List<Int> = listOf(),
    val mentionIds: List<Int> = listOf(),
    val attachment: Attachment? = null,
    val ownedByMe: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}


data class User(
    val name: String,
    val avatar: String? = null
)