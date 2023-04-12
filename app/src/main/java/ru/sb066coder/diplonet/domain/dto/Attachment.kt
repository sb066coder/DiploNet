package ru.sb066coder.diplonet.domain.dto

data class Attachment(
    val url: String,
    val type: AttachmentType
) {
    companion object {
        enum class AttachmentType {
            IMAGE, VIDEO, AUDIO
        }
    }
}