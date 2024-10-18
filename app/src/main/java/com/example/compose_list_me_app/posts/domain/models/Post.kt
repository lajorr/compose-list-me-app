package com.example.compose_list_me_app.posts.domain.models

data class Post(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)