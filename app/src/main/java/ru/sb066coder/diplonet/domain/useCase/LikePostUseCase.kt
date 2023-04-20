package ru.sb066coder.diplonet.domain.useCase

import ru.sb066coder.diplonet.domain.PostRepository
import ru.sb066coder.diplonet.domain.dto.Post

class LikePostUseCase(private val repository: PostRepository) {

    suspend operator fun invoke(post: Post, like: Boolean) {
        if (like) {
            repository.likePostById(post.id)
        } else {
            repository.unlikePostById(post.id)
        }
    }

    companion object {
        const val LIKE = true
        const val UNLIKE = false
    }
}