package com.example.compose_list_me_app.users.presentation

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.compose_list_me_app.R
import com.example.compose_list_me_app.ui.theme.BackgroundColor
import com.example.compose_list_me_app.ui.theme.PrimaryColor
import com.example.compose_list_me_app.ui.theme.SecondaryColor
import kotlinx.serialization.Serializable
import kotlin.math.round

@Serializable
object UserDetailScreen

@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier, userViewModel: UsersViewModel
) {

    val scrollState = rememberScrollState()
    val userData = userViewModel.userDetailState.user
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
                    .background(PrimaryColor)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                // User
                Box {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp)
                            .offset(y = 40.dp)
                            .shadow(8.dp), shape = RoundedCornerShape(8.dp)

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
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Albums()
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(stringResource(R.string.posts), fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        (1..5).map {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PrimaryColor)
                            )
                        }
                    }

                }
            }

        }
        else Text("Error")
    }

}

@Composable
fun UserInfoTile(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(100.dp)) {

        Text(title, fontWeight = FontWeight.Bold)
        Text(value, textAlign = TextAlign.Center)
    }
}

@Composable
fun Albums(modifier: Modifier = Modifier) {
    Text(stringResource(R.string.albums), fontSize = 24.sp)
    Spacer(modifier = Modifier.height(12.dp))
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(3) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryColor)
            )
        }
    }

}