package com.example.compose_list_me_app.comments.domain.repositories

import com.example.compose_list_me_app.comments.domain.models.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    suspend fun fetchCommentsOfPost(postId: Int): List<Comment>

    suspend fun addComment(comment: Comment)
    suspend fun getLocalCommentsOfPost(postId: Int): List<Comment>

}