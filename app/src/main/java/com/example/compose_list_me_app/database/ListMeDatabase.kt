package com.example.compose_list_me_app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.compose_list_me_app.comments.data.datasource.localDatasource.CommentDao
import com.example.compose_list_me_app.comments.domain.models.Comment
import com.example.compose_list_me_app.todo.data.datasource.todo_local_datasource.TodoDao
import com.example.compose_list_me_app.todo.domain.models.Todo

@Database(entities = [Comment::class, Todo::class], version = 1, exportSchema = false)
abstract class ListMeDatabase : RoomDatabase() {
    abstract val commentDao: CommentDao
    abstract val todoDao: TodoDao

}