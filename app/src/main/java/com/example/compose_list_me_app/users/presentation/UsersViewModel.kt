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
import com.example.compose_list_me_app.users.domain.models.album.Album
import com.example.compose_list_me_app.users.domain.models.photo.Photo
import com.example.compose_list_me_app.users.domain.models.user.User
import com.example.compose_list_me_app.users.domain.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


sealed interface UserUiState {
    data class Success(val usersList: List<User>) : UserUiState
    data object Loading : UserUiState
    data class Error(val message: String) : UserUiState
}

sealed interface AlbumUiState {
    data class Success(val albumList: List<Album>) : AlbumUiState
    data object Loading : AlbumUiState
    data class Error(val message: String) : AlbumUiState
}

sealed interface PhotoUiState {
    data class Success(val albumList: List<Photo>) : PhotoUiState
    data object Loading : PhotoUiState
    data class Error(val message: String) : PhotoUiState
}

data class UserDetailsState(val user: User)


class UsersViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()


    private val userList: StateFlow<List<User>> = userRepository.fetchAllUsers().catch {
        UserUiState.Error(message = "Failed to fetch data")
    }.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(5000L)
    )

    val userUiState: StateFlow<UserUiState> = combine(userList, searchQuery) { users, query ->
        val filteredUsers = if (query.isNotEmpty()) {
            users.filter {
                it.name.contains(query, ignoreCase = true)
            }
        } else {
            users
        }
        filteredUsers
    }.map {
        UserUiState.Success(usersList = it)
    }.catch {
        UserUiState.Error(message = "Failed to fetch data")
    }.stateIn(
        scope = viewModelScope,
        initialValue = UserUiState.Loading,
        started = SharingStarted.WhileSubscribed(5000L)
    )

    private val _userDetailState = MutableStateFlow<UserDetailsState?>(null)
    var userDetailState = _userDetailState.asStateFlow()
    var albumListState: AlbumUiState by mutableStateOf(AlbumUiState.Loading)
    var photoListState: PhotoUiState by mutableStateOf(PhotoUiState.Loading)


    fun clearSearchText() {
        _searchQuery.value = ""
    }

    fun updateSearchText(text: String) {
        _searchQuery.value = text
    }


    fun getUser(id: Int) {
        val user = userUiState.value.let { state ->
            if (state is UserUiState.Success) {
                state.usersList.find { it.id == id }
            } else {
                null
            }
        }
        _userDetailState.value = UserDetailsState(user = user!!)
    }

    fun getAllUserAlbums(userId: Int) {
        albumListState = AlbumUiState.Loading
        try {

            viewModelScope.launch {
                val result = userRepository.fetchUserAlbums(userId)
                albumListState = AlbumUiState.Success(albumList = result)

            }
        } catch (e: Exception) {
            albumListState = AlbumUiState.Error(message = "Failed to fetch albums")
        }
    }

    fun getAlbumPhotos(albumId: Int) {
        photoListState = PhotoUiState.Loading
        try {
            viewModelScope.launch {
                val result = userRepository.fetchAlbumPhotos(albumId)
                photoListState = PhotoUiState.Success(albumList = result)
            }
        } catch (e: Exception) {
            photoListState = PhotoUiState.Error("Failed to fetch photos")
        }
    }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ListMeApplication
                UsersViewModel(
                    userRepository = application.container.userRepository,
                )
            }
        }
    }
}