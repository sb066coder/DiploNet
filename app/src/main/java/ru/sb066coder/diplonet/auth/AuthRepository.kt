package ru.sb066coder.diplonet.auth

import androidx.lifecycle.LiveData
import java.io.File

interface AuthRepository {

    val data: LiveData<AuthState>
    val authenticated: Boolean

    /**
     * Returns null when success or error message otherwise
     * */
    suspend fun signIn(login: String, password: String): String?

    /**
     * Returns null when success or error message otherwise
     * */
    suspend fun signUp(name: String, login: String, password: String, file: File?): String?

    fun signOut()
}