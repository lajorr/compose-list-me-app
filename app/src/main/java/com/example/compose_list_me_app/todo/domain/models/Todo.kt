package com.example.compose_list_me_app.todo.domain.models

data class Todo(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)