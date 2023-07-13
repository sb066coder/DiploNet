package ru.sb066coder.diplonet.data.database

import androidx.room.Entity
import ru.sb066coder.diplonet.domain.dto.Attachment
import ru.sb066coder.diplonet.domain.dto.Coordinates

@Entity(tableName = "posts")
data class PostDbModel(
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
    val mentionedMe: Boolean,
    val likedByMe: Boolean,
    val attachment: Attachment? = null,
    val ownedByMe: Boolean,
    val id: Int
)
