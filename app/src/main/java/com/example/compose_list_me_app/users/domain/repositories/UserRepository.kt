package com.example.compose_list_me_app.users.domain.repositories

import com.example.compose_list_me_app.users.domain.models.User

interface UserRepository {
    suspend fun fetchAllUsers(): List<User>
}