package ru.sb066coder.diplonet.domain.useCase

import ru.sb066coder.diplonet.domain.PostRepository
import javax.inject.Inject

class GetPostListUseCase @Inject constructor (
    private val postRepository: PostRepository
) {
    suspend operator fun invoke() {
        postRepository.getPostList()
    }
}