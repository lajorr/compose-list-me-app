package com.example.compose_list_me_app

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class NavViewModel : ViewModel() {

    val selectedIndex = mutableIntStateOf(0)

    fun updateSelectedIndex(index: Int) {
        selectedIndex.value = index
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
//            val applicatio
                NavViewModel()
            }
        }
    }
}