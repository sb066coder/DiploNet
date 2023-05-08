package ru.sb066coder.diplonet.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import ru.sb066coder.diplonet.auth.AuthenticationRequest
import ru.sb066coder.diplonet.auth.Token
import ru.sb066coder.diplonet.domain.dto.Post

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