package ru.sb066coder.diplonet.presentation.viewmodel

import androidx.lifecycle.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.sb066coder.diplonet.auth.AppAuth
import ru.sb066coder.diplonet.auth.AuthState
import ru.sb066coder.diplonet.auth.AuthenticationRequest
import ru.sb066coder.diplonet.auth.Token
import ru.sb066coder.diplonet.data.api.Api
import ru.sb066coder.diplonet.data.api.ErrorResponse
import ru.sb066coder.diplonet.presentation.util.SingleLiveEvent
import ru.sb066coder.diplonet.presentation.view.WelcomeFragment

class AuthViewModel : ViewModel() {

    private val apiService = Api.service

    val data: LiveData<AuthState> =
        AppAuth.getInstance().authStateFlow.asLiveData(Dispatchers.Default)
    val authenticated: Boolean
        get() = AppAuth.getInstance().authStateFlow.value.id != 0
    private val _errorLogin = MutableLiveData<Boolean>()
    val errorLogin: LiveData<Boolean>
        get() = _errorLogin
    private val _errorPassword = MutableLiveData<Boolean>()
    val errorPassword: LiveData<Boolean>
        get() = _errorPassword
    private val _authDone = SingleLiveEvent<Unit>()
    val authDone: LiveData<Unit>
        get() = _authDone
    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun signIn(login: String, password: String) {
        if (login.isEmpty()) {
            _errorLogin.value = true
            return
        }
        if (password.isEmpty()) {
            _errorPassword.value = true
            return
        }
        viewModelScope.launch {
            val response = apiService.authorize(AuthenticationRequest(login, password))
            if (!response.isSuccessful) {
                _errorMessage.postValue("Access error: ${getErrorReason(response)}")
                return@launch
            }
            response.body()?.let {
                AppAuth.getInstance().setAuth(it.id, it.token)
                _authDone.postValue(Unit)
            } ?: throw RuntimeException("Body is empty")
        }
    }

    private fun getErrorReason(response: Response<Token>): String {
        return Gson().fromJson(
            response.errorBody()?.string(),
            ErrorResponse::class.java
        )
            .reason ?: "unknown"

    }

    fun signUp(login: String, password: String, confirmPassword: String, avatar: String? = null) {

    }

    fun signOut() {

    }

    fun cancelInputError(fieldName: String) {
        when (fieldName) {
            WelcomeFragment.LOGIN_FIELD_NAME -> {
                _errorLogin.value = false
            }
            WelcomeFragment.PASSWORD_FIELD_NAME -> {
                _errorPassword.value = false
            }
        }
    }

}

