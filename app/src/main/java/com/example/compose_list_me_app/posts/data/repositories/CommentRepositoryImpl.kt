package com.example.compose_list_me_app.posts.data.repositories

import com.example.compose_list_me_app.posts.data.datasource.CommentDatasource
import com.example.compose_list_me_app.posts.domain.models.Comment
import com.example.compose_list_me_app.posts.domain.repositories.CommentRepository

class CommentRepositoryImpl(private val commentDatasource: CommentDatasource) : CommentRepository {
    override suspend fun fetchCommentsOfPost(postId: Int): List<Comment> {

        var comments = listOf<Comment>()
        try {
            val response = commentDatasource.getCommentsOfPost(postId)
            if (response.isSuccessful) {
                response.body()?.let {
                    comments = it
                }
            }
            return comments
        } catch (e: Exception) {
            throw e
        }
    }
}