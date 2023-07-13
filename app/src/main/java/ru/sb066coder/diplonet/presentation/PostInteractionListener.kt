package ru.sb066coder.diplonet.presentation

import ru.sb066coder.diplonet.domain.dto.Post

interface PostInteractionListener {
    fun onLikeClick(id: Int, likedByMe: Boolean)
    fun onItemClick(id: Int)
}