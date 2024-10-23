package com.example.compose_list_me_app.todo.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.compose_list_me_app.ListMeApplication
import com.example.compose_list_me_app.todo.domain.models.Todo
import com.example.compose_list_me_app.todo.domain.repositories.TodoRepository
import kotlinx.coroutines.launch


sealed interface TodoUiState {
    data object Loading : TodoUiState
    data class Success(val todoList: List<Todo>) : TodoUiState
    data class Error(val message: String) : TodoUiState
}

class TodoViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    var todoUiState: TodoUiState by mutableStateOf(TodoUiState.Loading)
        private set


    fun getTodosByUserId(userId: Int) {
        Log.i(TAG, "getTodosByUserId: api call")
        viewModelScope.launch {
            TodoUiState.Loading
            try {
                val result = todoRepository.fetchUserTodos(userId)
                todoUiState = TodoUiState.Success(result)
            } catch (e: Exception) {
                TodoUiState.Error(message = "Failed to fetch todos")
            }
        }
    }


    companion object {
        private const val TAG = "todoVm"
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ListMeApplication
                TodoViewModel(todoRepository = application.container.todoRepository)
            }
        }
    }

}