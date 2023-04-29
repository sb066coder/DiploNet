package ru.sb066coder.diplonet.auth

data class AuthenticationRequest(
    val login: String,
    val password: String
)