package com.example.compose_list_me_app.users.data.datasource

import com.example.compose_list_me_app.users.domain.models.User
import retrofit2.Response
import retrofit2.http.GET

interface RemoteDataSource {
    @GET("users")
    suspend fun getAllUsers(): Response<List<User>>
}