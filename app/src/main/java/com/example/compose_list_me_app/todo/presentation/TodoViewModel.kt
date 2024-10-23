package com.example.compose_list_me_app.todo.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class TodoViewModel : ViewModel() {



    companion object {
        val Factory = viewModelFactory {
            initializer {
                TodoViewModel()
            }
        }
    }

}