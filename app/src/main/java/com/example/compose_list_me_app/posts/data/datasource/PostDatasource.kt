package com.example.compose_list_me_app.posts.data.datasource

import com.example.compose_list_me_app.posts.domain.models.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostDatasource {
    @GET("posts")
    suspend fun getAllPosts(): Response<List<Post>>

    @GET("users/{userId}/posts")
    suspend fun getUserPosts(@Path("userId") userId: Int): Response<List<Post>>
}