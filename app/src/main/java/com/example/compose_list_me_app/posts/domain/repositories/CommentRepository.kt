package com.example.compose_list_me_app.posts.domain.repositories

import com.example.compose_list_me_app.posts.domain.models.Comment

interface CommentRepository {
    suspend fun fetchCommentsOfPost(postId: Int): List<Comment>
}