package ru.sb066coder.diplonet.presentation

interface PostInteractionListener {
    fun onLikeClick(id: Int)
    fun onItemClick(id: Int)
}