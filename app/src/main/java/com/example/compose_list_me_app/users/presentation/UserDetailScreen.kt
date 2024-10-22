package com.example.compose_list_me_app.users.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.compose_list_me_app.R
import com.example.compose_list_me_app.common.ErrorText
import com.example.compose_list_me_app.posts.domain.models.Post
import com.example.compose_list_me_app.posts.presentations.PostTile
import com.example.compose_list_me_app.ui.theme.BackgroundColor
import com.example.compose_list_me_app.ui.theme.PrimaryColor
import com.example.compose_list_me_app.ui.theme.SecondaryColor
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailScreen(val id: Int)

@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    userViewModel: UsersViewModel,
    navigateBack: () -> Unit,
    onAlbumTap: (Int) -> Unit,
    userId: Int,
    postList: List<Post>,
    navigateCommentsScreen: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        userViewModel.getUser(id = userId)
    }

    val scrollState = rememberScrollState()
    val userData = userViewModel.userDetailState.user

    // for some reason i can press back btn multiple times...
    var backBtnState by remember {
        mutableStateOf(true)
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        if (userData != null) Box(
            modifier = Modifier
                .padding(bottom = it.calculateBottomPadding())
                .background(BackgroundColor)
        ) {

            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(PrimaryColor, PrimaryColor.copy(0.8f))
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ) {
                IconButton(
                    enabled = backBtnState, onClick = {
                        backBtnState = false
                        navigateBack()
                    }, modifier = Modifier.safeDrawingPadding()
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = stringResource(R.string.back)
                    )
                }
                // User
                Box {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp)
                            .offset(y = 40.dp)
                            .shadow(8.dp),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Column(
                            // User Info
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(40.dp))
                            Text(userData.name, fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(userData.email)
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                UserInfoTile(
                                    title = stringResource(R.string.street),
                                    value = userData.address.street
                                )
                                UserInfoTile(
                                    title = stringResource(R.string.city),
                                    value = userData.address.city
                                )
                                UserInfoTile(
                                    title = stringResource(R.string.zip_code),
                                    value = userData.address.zipcode
                                )
                            }
                        }
                    }
                    // Image
                    Surface(
                        modifier = Modifier
                            .size(80.dp)
                            .align(alignment = Alignment.TopCenter)
                            .border(3.dp, SecondaryColor, RoundedCornerShape(50))
                            .shadow(8.dp, RoundedCornerShape(50)), shape = RoundedCornerShape(50)

                    ) {
                        AsyncImage(model = userData.imageUrl, contentDescription = null)
                    }

                }
                Spacer(modifier = Modifier.height(50.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .weight(1f)
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Albums(
                        userViewModel.albumListState,
                        getAlbums = { userViewModel.getAllUserAlbums(userId = userData.id) },
                        onAlbumTap = onAlbumTap
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Posts(posts = postList, onPostTap = navigateCommentsScreen)

                }
            }


        }
        else Text("Error")
    }

}

@Composable
fun UserInfoTile(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(80.dp)
    ) {

        Text(title, fontWeight = FontWeight.Bold)
        Text(value, textAlign = TextAlign.Center)
    }
}

@Composable
fun Albums(
    albumUiState: AlbumUiState, getAlbums: () -> Unit, onAlbumTap: (Int) -> Unit
) {
    // init state
    LaunchedEffect(Unit) {
        getAlbums()
    }
    Column {
        Text(stringResource(R.string.albums), fontSize = 24.sp)
        Spacer(modifier = Modifier.height(12.dp))
        when (albumUiState) {
            is AlbumUiState.Error -> ErrorText(albumUiState.message)
            AlbumUiState.Loading -> Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is AlbumUiState.Success -> {

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(albumUiState.albumList) { album ->
                        Box(modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(PrimaryColor, PrimaryColor.copy(0.8f))
                                )
                            )
                            .clickable { onAlbumTap(album.id) }
                            .padding(12.dp)) {
                            Text(
                                album.title,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun Posts(modifier: Modifier = Modifier, posts: List<Post>, onPostTap: (Int) -> Unit) {
    Column(modifier = modifier) {
        Text(stringResource(R.string.posts), fontSize = 24.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            posts.forEachIndexed { index, post ->
                PostTile(
                    post = post,
                    index = index,
                    bgColor = BackgroundColor,
                    onTap = onPostTap
                )
            }
        }
    }
}