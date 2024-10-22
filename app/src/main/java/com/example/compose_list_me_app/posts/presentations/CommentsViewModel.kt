package com.example.compose_list_me_app.posts.presentations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.compose_list_me_app.ListMeApplication
import com.example.compose_list_me_app.posts.domain.models.Comment
import com.example.compose_list_me_app.posts.domain.repositories.CommentRepository
import kotlinx.coroutines.launch


sealed interface CommentUiState {
    data class Success(val postComments: List<Comment>) : CommentUiState
    data class Error(val message: String) : CommentUiState
    data object Loading : CommentUiState
}

class CommentsViewModel(private val commentRepository: CommentRepository) : ViewModel() {


    // Todo: allComments List
    // todo: filtered comments .. based on posts
    // todo: add comments to room database

    var commentUiState: CommentUiState by mutableStateOf(CommentUiState.Loading)
        private set

    var isDialogShown by mutableStateOf(false)
        private set

    fun onDismissDialog() {
        isDialogShown = false
    }

    fun onShowDialog() {
        isDialogShown = true
    }


    fun getCommentsByPostId(postId: Int) {
        commentUiState = CommentUiState.Loading
        try {
            viewModelScope.launch {
                val comments = commentRepository.fetchCommentsOfPost(postId)
                commentUiState = CommentUiState.Success(comments)
            }
        } catch (e: Exception) {
            commentUiState = CommentUiState.Error(message = "Failed to fetch comments..")
        }
    }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ListMeApplication
                CommentsViewModel(commentRepository = application.container.commentRepository)
            }
        }
    }


}