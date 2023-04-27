package ru.sb066coder.diplonet.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import ru.sb066coder.diplonet.auth.AppAuth
import ru.sb066coder.diplonet.auth.AuthState

class AuthViewModel : ViewModel() {

    val data: LiveData<AuthState> = AppAuth.getInstance()
    .authStateFlow
    .asLiveData(Dispatchers.Default)
    val authenticated: Boolean
        get() = AppAuth.getInstance().authStateFlow.value.id != 0

    fun signIn(login: String, password: String) {

    }
    fun signUp(login: String, password: String, confirmPassword: String, avatar: String? = null) {

    }
    fun signOut() {

    }

}
