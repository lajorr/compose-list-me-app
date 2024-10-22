package com.example.compose_list_me_app.comments.data.repositories

import com.example.compose_list_me_app.comments.data.datasource.CommentDatasource
import com.example.compose_list_me_app.comments.domain.models.Comment
import com.example.compose_list_me_app.comments.domain.repositories.CommentRepository

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