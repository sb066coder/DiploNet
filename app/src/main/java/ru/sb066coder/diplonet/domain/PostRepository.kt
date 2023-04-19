package ru.sb066coder.diplonet.domain

import ru.sb066coder.diplonet.domain.dto.Post

interface PostRepository {
    suspend fun getPostList(): List<Post>
    fun getPostById(id: Int): Post
    fun addPost(post: Post)
    fun deletePostById(id: Int)
    fun editPost(post: Post)
}