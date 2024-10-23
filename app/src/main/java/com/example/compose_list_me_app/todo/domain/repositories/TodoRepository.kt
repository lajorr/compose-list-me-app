package com.example.compose_list_me_app.todo.domain.repositories

import com.example.compose_list_me_app.todo.domain.models.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun fetchUserTodos(userId: Int): Flow<List<Todo>>

    suspend fun addTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
    suspend fun updateTodo(todo: Todo)

    fun fetchLocalUserTodos(userId: Int): Flow<List<Todo>>


}