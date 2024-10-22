package com.example.compose_list_me_app.posts.domain.repositories

import com.example.compose_list_me_app.posts.domain.models.Comment
import com.example.compose_list_me_app.posts.domain.models.Post

interface PostRepository {
    suspend fun fetchAllPosts(): List<Post>
    suspend fun fetchUserPosts(userId: Int): List<Post>
}