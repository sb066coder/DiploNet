package ru.sb066coder.diplonet.domain

import androidx.lifecycle.LiveData
import ru.sb066coder.diplonet.domain.dto.Post

interface PostRepository {
    val postList: LiveData<List<Post>>
    suspend fun getPostList()
    fun getPostById(id: Int): Post
    fun addPost(post: Post)
    fun deletePostById(id: Int)
    fun editPost(post: Post)
    suspend fun likePostById(id: Int)
    suspend fun unlikePostById(id: Int)
}