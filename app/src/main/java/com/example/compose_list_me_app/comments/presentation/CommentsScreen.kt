package com.example.compose_list_me_app.comments.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose_list_me_app.comments.domain.models.Comment
import com.example.compose_list_me_app.common.composables.ErrorText
import com.example.compose_list_me_app.common.composables.MyAppBar
import com.example.compose_list_me_app.posts.domain.models.Post
import com.example.compose_list_me_app.ui.theme.BackgroundColor
import com.example.compose_list_me_app.ui.theme.PrimaryColor
import com.example.compose_list_me_app.ui.theme.SecondaryColor
import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable
data class CommentsScreenObject(val postId: Int)


@Composable
fun CommentsScreen(
    onPop: () -> Unit,
    commentsViewModel: CommentsViewModel = viewModel(factory = CommentsViewModel.Factory),
    post: Post

) {
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundColor),
        topBar = { MyAppBar(title = "Comments", navigateBack = onPop) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = commentsViewModel::onShowDialog,
                containerColor = PrimaryColor
            ) {
                Icon(
                    Icons.Default.Add,
                    tint = Color.White,
                    contentDescription = "Add"
                )
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
//                    containerColor = Color.White
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = post.title.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        }, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = PrimaryColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = post.body,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(300),
                        color = PrimaryColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            CommentList(
                modifier = Modifier.weight(1f),
                getComments = { commentsViewModel.getCommentsByPostId(post.id) },
                commentUiState = commentsViewModel.commentUiState.collectAsState().value
            )

        }
        if (commentsViewModel.isDialogShown)
            CommentsDialog(
                viewModel = commentsViewModel,
                postId = post.id
            )
    }
}

@Composable
fun CommentList(
    modifier: Modifier = Modifier, getComments: () -> Unit, commentUiState: CommentUiState
) {
    LaunchedEffect(Unit) {
        getComments()
    }
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (commentUiState) {
            is CommentUiState.Error -> ErrorText(commentUiState.message)
            CommentUiState.Loading -> CircularProgressIndicator()
            is CommentUiState.Success -> LazyColumn(
                modifier, verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(commentUiState.postComments) { comment ->
                    CommentTile(comment = comment)
                }
            }
        }
    }


}

@Composable
fun CommentTile(comment: Comment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            Modifier.padding(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .border(
                        width = 2.dp, color = SecondaryColor, shape = RoundedCornerShape(50)
                    )
                    .background(PrimaryColor), contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Person,
                    tint = Color.White,
                    contentDescription = "person",
                )
            }

            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    comment.name,
                    fontSize = 16.sp,
                    color = PrimaryColor,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    comment.body, color = PrimaryColor
                )
            }

        }
    }

}

