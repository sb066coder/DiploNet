package ru.sb066coder.diplonet.domain

import ru.sb066coder.diplonet.domain.dto.Post

class GetPostDataUseCase(private val postRepository: PostRepository) {

    suspend fun getPostsList(): List<Post> {
        return postRepository.getPostsList()
    }

    fun getPostById(){}
}