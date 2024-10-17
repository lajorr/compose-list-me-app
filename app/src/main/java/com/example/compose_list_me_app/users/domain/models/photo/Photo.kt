package com.example.compose_list_me_app.users.domain.models.photo

data class Photo(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)