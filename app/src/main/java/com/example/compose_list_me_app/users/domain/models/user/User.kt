package com.example.compose_list_me_app.users.domain.models.user


data class User(
    val address: Address,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String,
    var imageUrl: String? = null
)