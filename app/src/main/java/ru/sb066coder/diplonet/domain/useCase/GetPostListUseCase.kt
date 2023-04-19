package ru.sb066coder.diplonet.domain.useCase

import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post

class GetPostListUseCase(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(): List<Post> {
        return postRepository.getPostList()
    }
}