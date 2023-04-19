package ru.sb066coder.diplonet.domain.useCase

import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post

class EditPostUseCase(
    private val postRepository: PostRepository
) {
    fun editPost(post: Post) {
        postRepository.editPost(post)
    }
}