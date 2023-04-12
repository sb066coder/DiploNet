package ru.sb066coder.diplonet.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sb066coder.diplonet.data.repository.PostRepositoryImpl
import ru.sb066coder.diplonet.domain.GetPostDataUseCase
import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post

class PostViewModel: ViewModel() {

    private val repository: PostRepository = PostRepositoryImpl()

    private val getPostDataUseCase = GetPostDataUseCase(repository)

    private val _data = MutableLiveData<List<Post>>()
    val data: LiveData<List<Post>>
        get() = _data

    fun getData() = viewModelScope.launch {
        _data.value = getPostDataUseCase.getPostsList()
    }
}