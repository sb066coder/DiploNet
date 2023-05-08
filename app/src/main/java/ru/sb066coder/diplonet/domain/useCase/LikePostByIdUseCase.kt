package ru.sb066coder.diplonet.domain.useCase

import ru.sb066coder.diplonet.domain.PostRepository
import javax.inject.Inject

class LikePostByIdUseCase @Inject constructor (private val repository: PostRepository) {

    suspend operator fun invoke(id: Int, like: Boolean) {
        if (like) {
            repository.likePostById(id)
        } else {
            repository.unlikePostById(id)
        }
    }
}