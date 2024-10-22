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


sealed interface CommentUiState {
    data class Success(val postComments: List<Comment>) : CommentUiState
    data class Error(val message: String) : CommentUiState
    data object Loading : CommentUiState
}

typealias TextEditingController = String


class CommentsViewModel(private val commentRepository: CommentRepository) : ViewModel() {


    // Todo: allComments List
    // todo: filtered comments .. based on posts
    // todo: add comments to room database

    var commentUiState: CommentUiState by mutableStateOf(CommentUiState.Loading)
        private set

    var isDialogShown by mutableStateOf(false)
        private set

    var nameController by mutableStateOf<TextEditingController>("")
        private set
    var emailController by mutableStateOf<TextEditingController>("")
        private set
    var commentController by mutableStateOf<TextEditingController>("")
        private set


    var nameErrorText: String? by mutableStateOf(null)
        private set

    var emailErrorText: String? by mutableStateOf(null)
        private set

    var commentErrorText: String? by mutableStateOf(null)
        private set

    private fun clearInputs() {
        nameController = ""
        emailController = ""
        commentController = ""
    }

    private fun clearErrorText() {
        nameErrorText = null
        emailErrorText = null
        commentErrorText = null
    }

    fun onDismissDialog() {
        clearInputs()
        clearErrorText()
        isDialogShown = false
    }

    fun onConfirmDialog() {
        // todo: validate input
        if (validateInput()) {
            // todo: add comment to database
            // todo: room database.....
            onDismissDialog()
        }
    }

    private fun validateInput(): Boolean {
        val truthValues = listOf(
            validateName(), validateEmail(), validateComment()
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

    private fun validateEmail(): Boolean {
        emailErrorText = null

        if (emailController.isEmpty()) {
            emailErrorText = "Email is required"
            return false
        } else {
            val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
            if (!emailRegex.matches(emailController)) {
                emailErrorText = "Invalid email"
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

    fun onEmailChange(newValue: String) {
        emailController = newValue
    }


    fun getCommentsByPostId(postId: Int) {
        commentUiState = CommentUiState.Loading
        // todo: also fetch from database...
        // todo: add query to select comments based on postId
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