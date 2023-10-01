package ru.sb066coder.diplonet.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
//import ru.sb066coder.diplonet.domain.useCase.GetPostListUseCase
import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post
import ru.sb066coder.diplonet.domain.useCase.GetPostListUseCase
import ru.sb066coder.diplonet.domain.useCase.LikePostByIdUseCase
import javax.inject.Inject

@HiltViewModel
class PostRollViewModel @Inject constructor (
    repository: PostRepository,
    private val getPostListUseCase: GetPostListUseCase,
    private val likePostByIdUseCase: LikePostByIdUseCase
): ViewModel() {


    val data = repository.data//LiveData<List<Post>>


    fun getData() = viewModelScope.launch {
        getPostListUseCase()
    }

    fun likePostById(id: Int, likedByMe: Boolean) = viewModelScope.launch{
        likePostByIdUseCase(id, !likedByMe)
    }
}