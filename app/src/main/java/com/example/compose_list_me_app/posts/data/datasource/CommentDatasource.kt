package com.example.compose_list_me_app.posts.data.datasource

import com.example.compose_list_me_app.posts.domain.models.Comment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentDatasource {
    @GET("posts/{postId}/comments")
    suspend fun getCommentsOfPost(@Path("postId") postId: Int): Response<List<Comment>>
}