package com.example.compose_list_me_app.comments.data.datasource

import com.example.compose_list_me_app.comments.domain.models.Comment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentRemoteDatasource {
    @GET("posts/{postId}/comments")
    suspend fun getCommentsOfPost(@Path("postId") postId: Int): Response<List<Comment>>
}