package ru.sb066coder.diplonet.data.repository

import ru.sb066coder.diplonet.data.api.PostApi
import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post

class PostRepositoryImpl : PostRepository {

    override suspend fun getPostList(): List<Post> {
        return PostApi.service.getPosts()
    }

    override fun getPostById(id: Int): Post {
        TODO("Not yet implemented")
    }

    override fun addPost(post: Post) {
        TODO("Not yet implemented")
    }

    override fun deletePostById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun editPost(post: Post) {
        TODO("Not yet implemented")
    }
}