package com.example.compose_list_me_app.todo.domain.repositories

import com.example.compose_list_me_app.todo.domain.models.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun fetchUserTodos(userId: Int): List<Todo>

    suspend fun addTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
    suspend fun updateTodo(todo: Todo)

    fun getAllTodos(): Flow<List<Todo>>


}