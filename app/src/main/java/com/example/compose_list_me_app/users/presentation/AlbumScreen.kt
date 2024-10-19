package com.example.compose_list_me_app.users.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.compose_list_me_app.R
import com.example.compose_list_me_app.common.CustomFlexibleTopAppBar
import com.example.compose_list_me_app.common.MyAppBar
import com.example.compose_list_me_app.ui.theme.PrimaryColor
import kotlinx.serialization.Serializable

@Serializable
data class AlbumScreenParams(val albumId: Int)

@Composable
fun AlbumScreen(
    modifier: Modifier = Modifier, albumId: Int, viewModel: UsersViewModel, navigateBack: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getAlbumPhotos(albumId)
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            MyAppBar(
                title = stringResource(R.string.photos),
                navigateBack = navigateBack,
            )
        }) {

        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (val uiState = viewModel.photoListState) {
                is PhotoUiState.Error -> Text(uiState.message)
                PhotoUiState.Loading -> CircularProgressIndicator()
                is PhotoUiState.Success -> LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(5),

                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.albumList) { photo ->
                        Box(
                            modifier = Modifier
//                                .size(100.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Gray)
                        ) {
                            AsyncImage(
                                modifier = Modifier.height(75.dp),
                                model = photo.thumbnailUrl,
                                contentScale = ContentScale.FillHeight,
                                contentDescription = null,
                                error = painterResource(R.drawable.ic_launcher_foreground),
                                placeholder = painterResource(R.drawable.ic_launcher_background)
                            )
                        }

                    }

                }
            }


        }
    }
}
