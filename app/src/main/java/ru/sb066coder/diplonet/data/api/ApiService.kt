package ru.sb066coder.diplonet.data.api

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.*
import ru.sb066coder.diplonet.auth.AppAuth
import ru.sb066coder.diplonet.domain.dto.Post
import java.util.concurrent.TimeUnit

// TODO("replace with legal authorization")
private const val AUTH_TOKEN = "03e28a93-9227-4212-8845-560c14e546a5"
private const val BASE_URL = "https://netomedia.ru/api/"

private val client = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
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

}