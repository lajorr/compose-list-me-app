package com.example.compose_list_me_app.todo.data.repositories

import com.example.compose_list_me_app.todo.data.datasource.TodoRemoteDatasource
import com.example.compose_list_me_app.todo.domain.models.Todo
import com.example.compose_list_me_app.todo.domain.repositories.TodoRepository

class TodoRepositoryImpl(private val todoRemoteDatasource: TodoRemoteDatasource) : TodoRepository {
    override suspend fun fetchUserTodos(userId: Int): List<Todo> {
        try {
            var result = emptyList<Todo>()
            val response = todoRemoteDatasource.getUserTodos(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    result = it
                }
            }
            return result
        } catch (e: Exception) {
            throw e
        }
    }
}