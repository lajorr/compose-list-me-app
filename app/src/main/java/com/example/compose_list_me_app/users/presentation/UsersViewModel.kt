package com.example.compose_list_me_app.users.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.compose_list_me_app.ListMeApplication
import com.example.compose_list_me_app.users.domain.models.User
import com.example.compose_list_me_app.users.domain.repositories.UserRepository
import kotlinx.coroutines.launch


sealed interface UserUiState {
    data class Success(val usersList: List<User>) : UserUiState
    data object Loading : UserUiState
    data class Error(val message: String) : UserUiState
}

class UsersViewModel(private val userRepository: UserRepository) : ViewModel() {

    var searchText by mutableStateOf("")
        private set

    var userDataState: UserUiState by mutableStateOf(UserUiState.Loading)

    init {
        getUsers()
    }

    private lateinit var _userList: List<User>

    private fun getUsers() {
        userDataState = UserUiState.Loading
        viewModelScope.launch {
            try {
                val data = userRepository.fetchAllUsers()
                _userList = data
                userDataState = UserUiState.Success(data)
            } catch (e: Exception) {
                userDataState = UserUiState.Error(message = "Failed to fetch data")
            }
        }
    }


    fun updateSearchText(text: String) {
        searchText = text
        searchUsers()
    }

    private fun searchUsers() {
        userDataState = UserUiState.Loading
        try {

            val filteredUsers = _userList.filter { user ->
                user.name.lowercase().contains(searchText)
            }
            userDataState = UserUiState.Success(filteredUsers)

        } catch (e: Exception) {
            userDataState = UserUiState.Error(message = "No Result")
        }

    }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ListMeApplication
                UsersViewModel(userRepository = application.container.userRepository)
            }
        }
    }
}