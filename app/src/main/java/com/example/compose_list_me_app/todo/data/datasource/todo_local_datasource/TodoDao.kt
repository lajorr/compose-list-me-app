package com.example.compose_list_me_app.todo.data.datasource.todo_local_datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.compose_list_me_app.todo.domain.models.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAllTodos(): Flow<List<Todo>>

    @Insert
    suspend fun addTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)
}