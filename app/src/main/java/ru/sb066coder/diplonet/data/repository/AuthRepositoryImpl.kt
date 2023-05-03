package ru.sb066coder.diplonet.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import ru.sb066coder.diplonet.auth.*
import ru.sb066coder.diplonet.data.api.Api
import ru.sb066coder.diplonet.data.api.ErrorResponse
import java.io.File

class AuthRepositoryImpl : AuthRepository {

    private val apiService = Api.service

    override val data: LiveData<AuthState>
        get() = AppAuth.getInstance().authStateFlow.asLiveData(Dispatchers.Default)
    override val authenticated: Boolean
        get() = AppAuth.getInstance().authStateFlow.value.id != 0


    override suspend fun signIn(login: String, password: String): String? {
        try {
            val response = apiService.authorize(AuthenticationRequest(login, password))
            if (!response.isSuccessful) {
                return "Access error: ${getErrorReason(response)}"
            }
            response.body()?.let {
                AppAuth.getInstance().setAuth(it.id, it.token)
                return null
            } ?: throw RuntimeException("Body is null")
        } catch (e: Exception) {
            return "Network error"
        }
    }

    override suspend fun signUp(
        name: String, login: String, password: String, file: File?
    ): String? {
        val media: MultipartBody.Part? = file?.let {
            MultipartBody.Part.createFormData("file", it.name, it.asRequestBody())
        }
        try {
            val response = apiService.register(
                login.toRequestBody(), password.toRequestBody(), name.toRequestBody(), media
            )
            if (!response.isSuccessful) {
                return "Access error: ${getErrorReason(response)}"
            }
            response.body()?.let {
                AppAuth.getInstance().setAuth(it.id, it.token)
                return null
            } ?: throw RuntimeException("Body is null")
        } catch (e: Exception) {
            return "Network error"
        }
    }

    override fun signOut() {
        AppAuth.getInstance().removeAuth()
    }

    private fun String.toRequestBody(): RequestBody =
        this.toRequestBody("text/plain".toMediaTypeOrNull())

    private fun getErrorReason(response: Response<Token>): String {
        return Gson().fromJson(
            response.errorBody()?.string(),
            ErrorResponse::class.java
        )
            .reason ?: "unknown"
    }
}