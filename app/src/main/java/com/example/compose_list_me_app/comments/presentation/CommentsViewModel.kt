package com.example.compose_list_me_app.comments.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.compose_list_me_app.ListMeApplication
import com.example.compose_list_me_app.comments.domain.models.Comment
import com.example.compose_list_me_app.comments.domain.repositories.CommentRepository
import kotlinx.coroutines.launch
import java.util.Date


sealed interface CommentUiState {
    data class Success(val postComments: List<Comment>) : CommentUiState
    data class Error(val message: String) : CommentUiState
    data object Loading : CommentUiState
}

typealias TextEditingController = String

class CommentsViewModel(
    private val commentRepository: CommentRepository,
) : ViewModel() {

    var commentUiState: CommentUiState by mutableStateOf(CommentUiState.Loading)
        private set

    var isDialogShown by mutableStateOf(false)
        private set

    var nameController by mutableStateOf("")
        private set

    var commentController by mutableStateOf("")
        private set


    var nameErrorText: String? by mutableStateOf(null)
        private set


    var commentErrorText: String? by mutableStateOf(null)
        private set


//    val postComments = commentRemoteRepository.getCommentsOfPost()

    private fun clearInputs() {
        nameController = ""
        commentController = ""
    }

    private fun clearErrorText() {
        nameErrorText = null
        commentErrorText = null
    }

    fun onDismissDialog() {
        clearInputs()
        clearErrorText()
        isDialogShown = false
    }

    fun onConfirmDialog(postId: Int) {
        // todo: validate input
        if (validateInput()) {
            // todo!!: convert input to Comment
            val comment = Comment(
                Date().time.toInt(), body = commentController, name = nameController, postId
            )

            viewModelScope.launch {
                commentRepository.addComment(comment)
                onDismissDialog()
            }
        }
    }

    private fun validateInput(): Boolean {
        val truthValues = listOf(
            validateName(), validateComment()
        )
        return truthValues.any { !it }.not()
    }

    private fun validateName(): Boolean {
        nameErrorText = null
        if (nameController.isEmpty()) {
            nameErrorText = "Name is required"
            return false

        } else {
            if (nameController.length < 3 || nameController.length > 20) {
                nameErrorText = "Name must be between 3 and 20 characters"
                return false
            }
        }
        return true
    }


    private fun validateComment(): Boolean {
        commentErrorText = null
        if (commentController.isEmpty()) {
            commentErrorText = "Comment is required"
            return false
        } else {
            if (commentController.length < 5 || commentController.length > 50) {
                commentErrorText = "Comment must be between 5 and 50 characters"
                return false
            }
        }
        return true
    }

    fun onShowDialog() {
        isDialogShown = true
    }


    fun onNameChange(newValue: String) {
        nameController = newValue
    }

    fun onCommentChange(newValue: String) {
        commentController = newValue
    }


    fun getCommentsByPostId(postId: Int) {
        commentUiState = CommentUiState.Loading

        try {
            viewModelScope.launch {
                val remoteComments = commentRepository.fetchCommentsOfPost(postId)
                val localComments = commentRepository.getLocalCommentsOfPost(postId).reversed()
                val comments = localComments + remoteComments
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
                CommentsViewModel(
                    commentRepository = application.container.commentRepository,
                )
            }
        }
    }


}