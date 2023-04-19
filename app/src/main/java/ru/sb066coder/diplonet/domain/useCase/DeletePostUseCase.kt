package ru.sb066coder.diplonet.domain.useCase

import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post

class DeletePostUseCase(
    private val postRepository: PostRepository
) {
    fun deletePost(post: Post) {
        postRepository.deletePostById(post.id)
    }
}