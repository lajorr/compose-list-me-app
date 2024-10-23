package com.example.compose_list_me_app.todo.domain.repositories

import com.example.compose_list_me_app.todo.domain.models.Todo

interface TodoRepository {
    suspend fun fetchUserTodos(userId: Int): List<Todo>
}