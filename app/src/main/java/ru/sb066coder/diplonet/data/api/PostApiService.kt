package ru.sb066coder.diplonet.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.*
import ru.sb066coder.diplonet.domain.dto.Post
import java.util.concurrent.TimeUnit

// TODO("replace with legal authorization")
private const val AUTH_TOKEN = "03e28a93-9227-4212-8845-560c14e546a5"
private const val BASE_URL = "https://netomedia.ru/api/"

private val client = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object PostApi {
    val service: PostApiService by lazy {
        retrofit.create()
    }
}

interface PostApiService {

    @Headers("Authorization: $AUTH_TOKEN")
    @GET("posts")
    suspend fun getPosts(): List<Post>


}