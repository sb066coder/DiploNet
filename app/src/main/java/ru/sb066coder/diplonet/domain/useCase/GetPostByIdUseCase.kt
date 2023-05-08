package ru.sb066coder.diplonet.domain.useCase

import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor (
    private val postRepository: PostRepository
) {
    operator fun invoke(id: Int): Post {
        return postRepository.getPostById(id)
    }
}