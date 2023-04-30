package ru.sb066coder.diplonet.presentation

interface PostInteractionListener {
    fun onLikeClick(id: Int, likedByMe: Boolean)
    fun onItemClick(id: Int)
}