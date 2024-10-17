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

data class UserDetailsState(val user: User?)


class UsersViewModel(private val userRepository: UserRepository) : ViewModel() {

    var searchText by mutableStateOf("")
        private set

    var userListState: UserUiState by mutableStateOf(UserUiState.Loading)

    private lateinit var _userList: List<User>

    init {
        getUsersList()
    }


    private fun getUsersList() {
        userListState = UserUiState.Loading
        viewModelScope.launch {
            try {
                val data = userRepository.fetchAllUsers()
                _userList = data
                userListState = UserUiState.Success(data)
            } catch (e: Exception) {
                userListState = UserUiState.Error(message = "Failed to fetch data")
            }
        }
    }


    fun updateSearchText(text: String) {
        searchText = text
        searchUsers()
    }

    private fun searchUsers() {
        userListState = UserUiState.Loading
        try {

            val filteredUsers = _userList.filter { user ->
                user.name.lowercase().contains(searchText)
            }
            userListState = UserUiState.Success(filteredUsers)

        } catch (e: Exception) {
            userListState = UserUiState.Error(message = "No Result")
        }

    }

    var userDetailState: UserDetailsState by mutableStateOf(UserDetailsState(user = null))
        private set


    fun getUser(id: Int) {
        val user = _userList.first { user ->
            user.id == id
        }
        userDetailState = UserDetailsState(user = user)
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