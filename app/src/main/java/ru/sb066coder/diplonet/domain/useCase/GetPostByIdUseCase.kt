package ru.sb066coder.diplonet.domain.useCase

import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post

class GetPostByIdUseCase(
    private val postRepository: PostRepository
) {
    operator fun invoke(id: Int): Post {
        return postRepository.getPostById(id)
    }
}