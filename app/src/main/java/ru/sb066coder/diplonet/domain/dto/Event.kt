package ru.sb066coder.diplonet.domain.dto

data class Event(
    val authorId: Int,
    val author: String,
    val authorAvatar: String? = null,
    val authorJob: String? = null,
    val content: String,
    val datetime: String, // in event
    val published: String,
    val coords: Coordinates? = null,
    val type: String, // in event
    val link: String? = null,
    val likeOwnerIds: List<Int> = listOf(),
    val mentionIds: List<Int> = listOf(),
    val mentionedMe: Boolean,
    val likedByMe: Boolean,
    val speakerIds: List<Int> = listOf(), // in event
    val participantsIds: List<Int> = listOf(), // in event
    val participatedByMe: Boolean, // in event
    val attachment: Attachment? = null,
    val ownedByMe: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
