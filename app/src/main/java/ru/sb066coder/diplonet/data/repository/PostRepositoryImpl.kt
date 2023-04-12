package ru.sb066coder.diplonet.data.repository

import ru.sb066coder.diplonet.data.api.PostApi
import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post

class PostRepositoryImpl : PostRepository {

    override suspend fun getPostsList(): List<Post> {
        return PostApi.service.getPosts()
    }
}