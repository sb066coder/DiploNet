package ru.sb066coder.diplonet.data.database

import android.util.Log
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import ru.sb066coder.diplonet.domain.dto.Attachment
import ru.sb066coder.diplonet.domain.dto.Coordinates
import ru.sb066coder.diplonet.domain.dto.Post

@Entity
data class PostDbModel(
    val authorId: Int,
    val author: String,
    val authorAvatar: String? = null,
    val authorJob: String? = null,
    val content: String,
    val published: String,
    @Embedded
    val coords: CoordinatesEmbeddable?,
    val link: String? = null,
    val likeOwnerIds: List<Int>,
    val mentionIds: List<Int>,
    val mentionedMe: Boolean,
    val likedByMe: Boolean,
    @Embedded
    val attachment: AttachmentEmbeddable? = null,
    val ownedByMe: Boolean,
    @PrimaryKey(false)
    val id: Int
) {
    fun toDto() = Post(
        authorId,
        author,
        authorAvatar,
        authorJob,
        content,
        published,
        coords = coords?.toDto(),
        link,
        likeOwnerIds = likeOwnerIds,
        mentionIds = mentionIds,
        mentionedMe,
        likedByMe,
        attachment = attachment?.toDto(),
        ownedByMe,
        id
    )

    companion object {
        fun fromDto(dto: Post) = PostDbModel(
            dto.authorId,
            dto.author,
            dto.authorAvatar,
            dto.authorJob,
            dto.content,
            dto.published,
            CoordinatesEmbeddable.fromDto(dto.coords),
            dto.link,
            likeOwnerIds = dto.likeOwnerIds,
            mentionIds = dto.mentionIds,
            dto.mentionedMe,
            dto.likedByMe,
            attachment = AttachmentEmbeddable.fromDto(dto.attachment),
            dto.ownedByMe,
            dto.id
        )
    }
}

fun List<PostDbModel>.toDto(): List<Post> = map(PostDbModel::toDto)
fun List<Post>.fromDto(): List<PostDbModel> = map(PostDbModel::fromDto)

data class AttachmentEmbeddable(
    var url: String,
    var type: Attachment.Companion.AttachmentType
) {
    fun toDto() = Attachment(url, type)

    companion object {
        fun fromDto(dto: Attachment?) = dto?.let {
            AttachmentEmbeddable(it.url, it.type)
        }
    }
}

data class CoordinatesEmbeddable(
    val latitude: String,
    val longitude: String
) {
    fun toDto() = Coordinates(latitude, longitude)

    companion object {
        fun fromDto(dto: Coordinates?) = dto?.let {
            CoordinatesEmbeddable(it.lat, it.long)
        }
    }
}
class Converters {
    @TypeConverter
    fun toAttachmentType(value: String) = enumValueOf<Attachment.Companion.AttachmentType>(value)
    @TypeConverter
    fun fromAttachmentType(value: Attachment.Companion.AttachmentType) = value.name
    @TypeConverter
    fun fromListInt(list: List<Int>): String {
        return list.fold("") { acc, int ->
            "$acc $int "
        }
    }
    @TypeConverter
    fun toListInt(string: String): List<Int> {
        Log.i("toListInt", string)
        return string.split(" ")
            .toMutableList()
            .also { it.removeIf { item -> item == "" } }
            .map { it.toInt() }
    }
}