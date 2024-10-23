package com.example.compose_list_me_app.todo.data.datasource

import com.example.compose_list_me_app.todo.domain.models.Todo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TodoRemoteDatasource {
    @GET("users/{userId}/todos")
    suspend fun getUserTodos(@Path("userId") userId: Int): Response<List<Todo>>
}