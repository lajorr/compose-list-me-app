package com.example.compose_list_me_app.users.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.compose_list_me_app.R
import com.example.compose_list_me_app.ui.theme.PrimaryColor
import kotlinx.serialization.Serializable

@Serializable
data class AlbumScreenParams(val albumId: Int)

@Composable
fun AlbumScreen(modifier: Modifier = Modifier, albumId: Int, viewModel: UsersViewModel) {

    LaunchedEffect(Unit) {
        viewModel.getAlbumPhotos(albumId)
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { Appbar() }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Appbar(modifier: Modifier = Modifier) {
    TopAppBar(

        title = {
            Text(
                stringResource(R.string.photos),
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor)
    )

}