package com.example.compose_list_me_app.posts.presentations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
data class CommentsScreenObject(val postId: Int)

@Composable
fun CommentsScreen(modifier: Modifier = Modifier, postId: Int) {
    Scaffold {
        Column(modifier = Modifier.padding(it)
        ) {
            Text("asd")
        }
}

}