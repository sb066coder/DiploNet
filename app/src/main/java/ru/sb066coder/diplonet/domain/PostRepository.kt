package ru.sb066coder.diplonet.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.sb066coder.diplonet.domain.dto.Post

interface PostRepository {
    val data: Flow<PagingData<Post>>
    suspend fun getPostList()
    fun getPostById(id: Int): Post
    fun addPost(post: Post)
    fun deletePostById(id: Int)
    fun editPost(post: Post)
    suspend fun likePostById(id: Int)
    suspend fun unlikePostById(id: Int)
}