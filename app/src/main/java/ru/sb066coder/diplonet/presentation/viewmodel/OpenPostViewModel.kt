package ru.sb066coder.diplonet.presentation.viewmodel

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

    fun getPostById(id: Int): Post {
        return getPostByIdUseCase(id)
    }

    fun likePostById(id: Int, likedByMe: Boolean) = viewModelScope.launch{
        likePostByIdUseCase(id, !likedByMe)
    }
}