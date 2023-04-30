package ru.sb066coder.diplonet.domain.useCase

import ru.sb066coder.diplonet.domain.PostRepository

class LikePostByIdUseCase(private val repository: PostRepository) {

    suspend operator fun invoke(id: Int, like: Boolean) {
        if (like) {
            repository.likePostById(id)
        } else {
            repository.unlikePostById(id)
        }
    }
}