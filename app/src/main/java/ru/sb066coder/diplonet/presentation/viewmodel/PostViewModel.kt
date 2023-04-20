package ru.sb066coder.diplonet.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sb066coder.diplonet.data.repository.PostRepositoryImpl
import ru.sb066coder.diplonet.domain.useCase.GetPostListUseCase
import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post
import ru.sb066coder.diplonet.domain.useCase.GetPostByIdUseCase
import ru.sb066coder.diplonet.domain.useCase.LikePostUseCase

class PostViewModel: ViewModel() {

    private val repository: PostRepository = PostRepositoryImpl()

    private val getPostListUseCase = GetPostListUseCase(repository)
    private val getPostByIdUseCase = GetPostByIdUseCase(repository)
    private val likePostUseCase = LikePostUseCase(repository)


    private val _data = repository.postList as MutableLiveData
    val data: LiveData<List<Post>>
        get() = _data


    fun getData() = viewModelScope.launch {
        getPostListUseCase()
    }

    fun likePostById(id: Int) = viewModelScope.launch{
        val post = getPostByIdUseCase(id)
        likePostUseCase(
            post,
            if (!post.likedByMe) {
                LikePostUseCase.LIKE
            } else {
                LikePostUseCase.UNLIKE
            }
        )
    }
}