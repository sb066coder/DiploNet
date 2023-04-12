package ru.sb066coder.diplonet.domain

import ru.sb066coder.diplonet.domain.dto.Post

interface PostRepository {
    suspend fun getPostsList(): List<Post>
}