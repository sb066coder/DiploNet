package ru.sb066coder.diplonet.domain.useCase

import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post

class AddPostUseCase(
    private val postRepository: PostRepository
) {
    fun addPost(post: Post) {
        postRepository.addPost(post)
    }
}