package com.example.compose_list_me_app.todo.data.repositories

import com.example.compose_list_me_app.todo.data.datasource.TodoRemoteDatasource
import com.example.compose_list_me_app.todo.data.datasource.todo_local_datasource.TodoDao
import com.example.compose_list_me_app.todo.domain.models.Todo
import com.example.compose_list_me_app.todo.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TodoRepositoryImpl(
    private val todoRemoteDatasource: TodoRemoteDatasource, private val todoDao: TodoDao
) : TodoRepository {
    override fun fetchUserTodos(userId: Int): Flow<List<Todo>> = flow {
        try {
            val response = todoRemoteDatasource.getUserTodos(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun addTodo(todo: Todo) = todoDao.addTodo(todo)

    override suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)

    override suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(todo)


    override fun fetchLocalUserTodos(userId: Int): Flow<List<Todo>> =
        todoDao.getUserTodos(userId)
}