package com.example.compose_list_me_app.todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.compose_list_me_app.ListMeApplication
import com.example.compose_list_me_app.todo.domain.models.Todo
import com.example.compose_list_me_app.todo.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date


sealed interface TodoUiState {
    data object Loading : TodoUiState
    data class Success(
        val remoteTodos: List<Todo>, val localTodos: List<Todo>
    ) : TodoUiState

    data class Error(val message: String) : TodoUiState
}

class TodoViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    private val _remoteTodoList = MutableStateFlow<List<Todo>>(emptyList())
    private val remoteTodoList: StateFlow<List<Todo>> = _remoteTodoList

    private val _localTodoList = MutableStateFlow<List<Todo>>(emptyList())
    private val localTodoList: StateFlow<List<Todo>> = _localTodoList.asStateFlow()

    val todoUiState: StateFlow<TodoUiState> =
        combine(remoteTodoList, localTodoList) { remoteTodos, localTodos ->
            TodoUiState.Success(remoteTodos = remoteTodos, localTodos = localTodos)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = TodoUiState.Loading
        )

    private val _bottomState = MutableStateFlow(false)
    val showBottomSheet = _bottomState.asStateFlow()

    private val _todoInputController = MutableStateFlow("")
    val todoInputController = _todoInputController.asStateFlow()


    fun getTodosByUserId(userId: Int) {
        viewModelScope.launch {
            TodoUiState.Loading
            try {
                todoRepository.fetchLocalUserTodos(userId).collectLatest {
                    _localTodoList.value = it
                }
                todoRepository.fetchUserTodos(userId).collectLatest {
                    _remoteTodoList.value = it
                }
            } catch (e: Exception) {
                TodoUiState.Error(message = "Failed to fetch todos")
            }
        }
    }

    fun toggleBottomSheetState(todo: Todo? = null) {
        _todoInputController.value = ""
        if (todo != null) {
            _todoInputController.value = todo.title
//            _currentTodo.value = todo
        }
        _bottomState.value = !_bottomState.value
    }

    fun onTextChange(value: String) {
        _todoInputController.value = value
    }

    fun addTodo(userId: Int) {
        if (_todoInputController.value.trim().isEmpty()) return
        val todo = Todo(
            id = Date().time.toInt(),
            title = _todoInputController.value,
            userId = userId,
            completed = false
        )

        viewModelScope.launch {
            todoRepository.addTodo(todo)
            toggleBottomSheetState()
            getTodosByUserId(userId)
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