package com.example.compose_list_me_app.posts.presentations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.compose_list_me_app.ListMeApplication
import com.example.compose_list_me_app.posts.domain.models.Comment
import com.example.compose_list_me_app.posts.domain.models.Post
import com.example.compose_list_me_app.posts.domain.repositories.PostRepository
import kotlinx.coroutines.launch


sealed interface PostUiState {
    data class Success(val allPosts: List<Post>) : PostUiState
    data class Error(val message: String) : PostUiState
    data object Loading : PostUiState
}



class PostViewModel(private val postRepository: PostRepository) : ViewModel() {

    var postUiState: PostUiState by mutableStateOf(PostUiState.Loading)
        private set

    private var allPostList = mutableStateListOf<Post>()



    init {
        getAllPosts()
    }

    private fun getAllPosts() {
        PostUiState.Loading
        allPostList.clear()
        try {
            viewModelScope.launch {
                val posts = postRepository.fetchAllPosts()
                allPostList.addAll(posts)
                postUiState = PostUiState.Success(posts)
            }
        } catch (e: Exception) {
            postUiState = PostUiState.Error("Failed to Fetch Posts")
        }
    }

    fun getUserPosts(userId: Int): List<Post> {
        val a = allPostList.filter { it.userId == userId }
        return a
    }

    fun getPostById(postId: Int): Post {
        return allPostList.first { it.id == postId }
    }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ListMeApplication
                PostViewModel(postRepository = application.container.postRepository)
            }
        }
    }
}