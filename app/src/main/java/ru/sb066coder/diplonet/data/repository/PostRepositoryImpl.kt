package ru.sb066coder.diplonet.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Response
import ru.sb066coder.diplonet.data.api.Api
import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post

class PostRepositoryImpl : PostRepository {

    private val _postList = MutableLiveData<List<Post>>()
    override val postList: LiveData<List<Post>>
        get() = _postList

    override suspend fun getPostList() {
        try {
            val response = Api.service.getPosts()
            if (!response.isSuccessful) {
                throw RuntimeException(response.message())
            }
            _postList.postValue(response.body())
        } catch (e: Exception) {
            throw RuntimeException("Response body error")
        }
    }

    override fun getPostById(id: Int): Post {
        return postList.value?.firstOrNull { post ->
            post.id == id
        } ?: throw RuntimeException("No post with id $id")
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

    override suspend fun likePostById(id: Int) {
        try {
            val response = Api.service.likePostById(id)
            updatePostList(response, id)
        } catch (e: Exception) {
            throw RuntimeException("Net exception")
        }
    }

    override suspend fun unlikePostById(id: Int) {
        try {
            val response = Api.service.unlikePostById(id)
            updatePostList(response, id)
        } catch (e: Exception) {
            throw RuntimeException("Net exception")
        }
    }

    private fun updatePostList(
        response: Response<Post>,
        id: Int
    ) {
        if (!response.isSuccessful) {
            throw RuntimeException(response.message())
        }
        val newPost = response.body() ?: throw RuntimeException("Body == null")
        _postList.value = postList.value?.map { post ->
            if (post.id != id) {
                post
            } else {
                newPost
            }
        }
    }
}