package ru.sb066coder.diplonet.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Response
import ru.sb066coder.diplonet.data.api.ApiService
import ru.sb066coder.diplonet.data.database.PostDao
import ru.sb066coder.diplonet.data.database.PostDbModel
import ru.sb066coder.diplonet.data.paging.PostPagingSource
import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor (
    private val apiService: ApiService,
    private val postDao: PostDao
) : PostRepository {

    private val _postList = MutableLiveData<List<Post>>()
    override val data: Flow<PagingData<Post>> = getNewPager()

    private fun getNewPager(): Flow<PagingData<Post>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { PostPagingSource(apiService, postDao) }
    ).flow

    override suspend fun getPostList() {
        try {
            val response = apiService.getPosts()
            if (!response.isSuccessful) {
                throw RuntimeException(response.message())
            }
            _postList.postValue(response.body())
        } catch (e: Exception) {
            throw RuntimeException("Response body error")
        }
    }

    override suspend fun getPostById(id: Int): Post {
        return postDao.getPostById(id).toDto()
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
            val response = apiService.likePostById(id)
            updatePostList(response)
        } catch (e: Exception) {
            throw RuntimeException("Net exception")
        }
    }

    override suspend fun unlikePostById(id: Int) {
        try {
            val response = apiService.unlikePostById(id)
            updatePostList(response)
        } catch (e: Exception) {
            throw RuntimeException("Net exception")
        }
    }

    private suspend fun updatePostList(
        response: Response<Post>
    ) {
//        TODO("Not yet implemented")
        if (!response.isSuccessful) {
            throw RuntimeException(response.message())
        }
        val newPost = response.body() ?: throw RuntimeException("Body == null")
        postDao.insert(listOf(PostDbModel.fromDto(newPost)))
//        _postList.value = postList.value?.map { post ->
//            if (post.id != id) {
//                post
//            } else {
//                newPost
//            }
//        }
    }
}