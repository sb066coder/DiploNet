package ru.sb066coder.diplonet.data.api

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.*
import ru.sb066coder.diplonet.auth.AppAuth
import ru.sb066coder.diplonet.auth.AuthenticationRequest
import ru.sb066coder.diplonet.auth.Token
import ru.sb066coder.diplonet.domain.dto.Post
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://netomedia.ru/api/"

private val client = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .addInterceptor { chain ->
        AppAuth.getInstance().authStateFlow.value.token?.let { token ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", token)
                .build()
            return@addInterceptor chain.proceed(newRequest)
        }
        chain.proceed(chain.request())
    }
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object Api {
    val service: ApiService by lazy {
        retrofit.create()
    }
}

interface ApiService {

    
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @POST("posts/{id}/likes")
    suspend fun likePostById(@Path("id") id: Int): Response<Post>

    @DELETE("posts/{id}/likes")
    suspend fun unlikePostById(@Path("id") id: Int): Response<Post>

    @POST("users/authentication")
    suspend fun authorize(@Body authenticationRequest: AuthenticationRequest): Response<Token>

    @Multipart
    @POST("users/registration")
    suspend fun register(
        @Part("login") login: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody,
        @Part file: MultipartBody.Part?
        ): Response<Token>

}