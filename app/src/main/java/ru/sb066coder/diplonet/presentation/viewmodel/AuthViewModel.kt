package ru.sb066coder.diplonet.presentation.viewmodel

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.sb066coder.diplonet.R
import ru.sb066coder.diplonet.auth.AuthRepository
import ru.sb066coder.diplonet.auth.AuthState
import ru.sb066coder.diplonet.data.repository.AuthRepositoryImpl
import ru.sb066coder.diplonet.presentation.util.PhotoModel
import ru.sb066coder.diplonet.presentation.util.SingleLiveEvent
import ru.sb066coder.diplonet.presentation.view.WelcomeFragment
import java.io.File

class AuthViewModel : ViewModel() {

    private val repository: AuthRepository = AuthRepositoryImpl()

    val data: LiveData<AuthState> = repository.data
    val authenticated: Boolean
        get() = repository.authenticated
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
            repository.signIn(login, password)?.let {
                _errorMessage.postValue(it)
            } ?: _authDone.postValue(Unit)
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
        viewModelScope.launch {
            repository.signUp(name, login, password, photo.value?.file)?.let {
                _errorMessage.postValue(it)
            } ?: _authDone.postValue(Unit)
        }
    }


    //TODO: переложить метод в целевой фрагмент
    fun signOut(fragment: Fragment) {
        repository.signOut()
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

