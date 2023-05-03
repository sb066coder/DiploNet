package ru.sb066coder.diplonet.presentation.viewmodel

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import ru.sb066coder.diplonet.R
import ru.sb066coder.diplonet.auth.AppAuth
import ru.sb066coder.diplonet.auth.AuthState
import ru.sb066coder.diplonet.auth.AuthenticationRequest
import ru.sb066coder.diplonet.auth.Token
import ru.sb066coder.diplonet.data.api.Api
import ru.sb066coder.diplonet.data.api.ErrorResponse
import ru.sb066coder.diplonet.presentation.util.PhotoModel
import ru.sb066coder.diplonet.presentation.util.SingleLiveEvent
import ru.sb066coder.diplonet.presentation.view.WelcomeFragment
import java.io.File

class AuthViewModel : ViewModel() {

    private val apiService = Api.service

    val data: LiveData<AuthState> =
        AppAuth.getInstance().authStateFlow.asLiveData(Dispatchers.Default)
    val authenticated: Boolean
        get() = AppAuth.getInstance().authStateFlow.value.id != 0
    private val _errorName = MutableLiveData<Boolean>()
    val errorName: LiveData<Boolean>
        get() = _errorName
    private val _errorLogin = MutableLiveData<Boolean>()
    val errorLogin: LiveData<Boolean>
        get() = _errorLogin
    private val _errorPassword = MutableLiveData<Boolean>()
    val errorPassword: LiveData<Boolean>
        get() = _errorPassword
    private val _errorConfirmPassword = MutableLiveData<Boolean>()
    val errorConfirmPassword: LiveData<Boolean>
        get() = _errorConfirmPassword
    private val _authDone = SingleLiveEvent<Unit>()
    val authDone: LiveData<Unit>
        get() = _authDone
    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    private val noPhoto = PhotoModel()
    private val _photo = MutableLiveData(noPhoto)
    val photo: LiveData<PhotoModel>
        get() = _photo

    fun signIn(login: String, password: String) {
        if (!verifyFields(login = login, password = password)) return
        viewModelScope.launch {
            try {
                val response = apiService.authorize(AuthenticationRequest(login, password))
                if (!response.isSuccessful) {
                    _errorMessage.postValue("Access error: ${getErrorReason(response)}")
                    return@launch
                }
                response.body()?.let {
                    AppAuth.getInstance().setAuth(it.id, it.token)
                    _authDone.postValue(Unit)
                } ?: throw RuntimeException("Body is null")
            } catch (e: Exception) {
                _errorMessage.postValue("Network error")
            }
        }
    }


    fun signUp(
        name: String,
        login: String,
        password: String,
        confirmPassword: String
    ) {
        if (!verifyFields(
                name = name, login = login, password = password, confirmPassword = confirmPassword
            )) return
        val media: MultipartBody.Part? = photo.value?.file?.let {
            MultipartBody.Part.createFormData("file", it.name, it.asRequestBody())
        }
        viewModelScope.launch {
            try {
                val response = apiService.register(
                    login.toRequestBody(),
                    password.toRequestBody(),
                    name.toRequestBody(),
                    media
                )
                if (!response.isSuccessful) {
                    _errorMessage.postValue("Access error: ${getErrorReason(response)}")
                    return@launch
                }
                response.body()?.let {
                    AppAuth.getInstance().setAuth(it.id, it.token)
                    _authDone.postValue(Unit)
                } ?: throw RuntimeException("Body is null")
            } catch (e: Exception) {
                _errorMessage.postValue("Network error")
            }
        }
    }

    private fun String.toRequestBody(): RequestBody =
        this.toRequestBody("text/plain".toMediaTypeOrNull())

    //TODO: переложить метод в целевой фрагмент
    fun signOut(fragment: Fragment) {
        AppAuth.getInstance().removeAuth()
        fragment.findNavController().navigate(R.id.authCheckFragment)
    }

    private fun verifyFields(
        name: String? = null,
        login: String,
        password: String,
        confirmPassword: String? = null
    ): Boolean {
        var result = true
        if (login.isEmpty()) {
            _errorLogin.value = true
            result = false
        }
        if (password.isEmpty()) {
            _errorPassword.value = true
            result = false
        }
        if (name != null && name.isEmpty()) {
            _errorName.value = true
            result = false
        }
        if (confirmPassword != null && confirmPassword != password) {
            _errorConfirmPassword.value = true
            result = false
        }
        return result
    }

    private fun getErrorReason(response: Response<Token>): String {
        return Gson().fromJson(
            response.errorBody()?.string(),
            ErrorResponse::class.java
        )
            .reason ?: "unknown"
    }
    fun cancelInputError(fieldName: String) {
        when (fieldName) {
            WelcomeFragment.NAME_FIELD_NAME -> {
                _errorName.value = false
            }
            WelcomeFragment.LOGIN_FIELD_NAME -> {
                _errorLogin.value = false
            }
            WelcomeFragment.PASSWORD_FIELD_NAME -> {
                _errorPassword.value = false
            }
            WelcomeFragment.CONFIRM_PASSWORD_FIELD_NAME -> {
                _errorConfirmPassword.value = false
            }
        }
    }

    fun changePhoto(uri: Uri?, file: File?) {
        _photo.value = PhotoModel(uri, file)
    }

}

