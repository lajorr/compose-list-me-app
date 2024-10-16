package com.example.compose_list_me_app.users.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UsersViewModel : ViewModel() {

    var searchText by mutableStateOf("")
        private set

    fun updateSearchText(text: String) {
        searchText = text
    }
}