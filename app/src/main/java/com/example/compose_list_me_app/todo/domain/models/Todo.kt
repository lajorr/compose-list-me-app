package com.example.compose_list_me_app.todo.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey
    val id: Int,
    val completed: Boolean,
    val title: String,
    val userId: Int,
)