package com.example.compose_list_me_app.posts.data.datasource

import com.example.compose_list_me_app.posts.domain.models.Comment
import com.example.compose_list_me_app.posts.domain.models.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostDatasource {
    @GET("posts")
    suspend fun getAllPosts(): Response<List<Post>>

    @GET("users/{userId}/posts")
    suspend fun getUserPosts(@Path("userId") userId: Int): Response<List<Post>>

    @GET("posts/{postId}/comments")
    suspend fun getCommentsOfPost(@Path("postId") postId: Int): Response<List<Comment>>
}