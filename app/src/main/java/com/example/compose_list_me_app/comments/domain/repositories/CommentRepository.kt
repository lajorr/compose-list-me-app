package com.example.compose_list_me_app.comments.domain.repositories

import com.example.compose_list_me_app.comments.domain.models.Comment

interface CommentRepository {
    suspend fun fetchCommentsOfPost(postId: Int): List<Comment>
}