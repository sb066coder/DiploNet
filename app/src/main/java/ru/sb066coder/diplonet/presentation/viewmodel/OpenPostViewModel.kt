package ru.sb066coder.diplonet.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.sb066coder.diplonet.domain.dto.Post
import ru.sb066coder.diplonet.domain.useCase.GetPostByIdUseCase
import ru.sb066coder.diplonet.domain.useCase.LikePostByIdUseCase
import javax.inject.Inject

@HiltViewModel
class OpenPostViewModel @Inject constructor(
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val likePostByIdUseCase: LikePostByIdUseCase
) : ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post>
        get() = _post

    fun getPostById(id: Int) {
        viewModelScope.launch {
            _post.value = getPostByIdUseCase(id) ?: throw RuntimeException("post is NULL")
        }
    }

    fun likePostById(id: Int, likedByMe: Boolean) = viewModelScope.launch{
        likePostByIdUseCase(id, !likedByMe)
    }
}