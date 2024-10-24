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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.compose_list_me_app.R
import com.example.compose_list_me_app.common.composables.ElevatedCard
import com.example.compose_list_me_app.common.composables.ErrorText
import com.example.compose_list_me_app.common.composables.MyAppBar
import com.example.compose_list_me_app.common.composables.IconText
import com.example.compose_list_me_app.common.composables.CustomTextField
import com.example.compose_list_me_app.ui.theme.BackgroundColor
import com.example.compose_list_me_app.ui.theme.PrimaryColor
import com.example.compose_list_me_app.ui.theme.SecondaryColor
import com.example.compose_list_me_app.users.domain.models.user.User

@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    viewModel: UsersViewModel = viewModel(factory = UsersViewModel.Factory),
    onUserTap: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            MyAppBar(
                title = stringResource(R.string.users), canPop = false
            )
            CustomTextField(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 20.dp)
                    .offset(y = 25.dp),
                onTextChange = viewModel::updateSearchText,
                textController = viewModel.searchText,
                trailingIcon = {
                    IconButton(onClick = viewModel::clearSearchText) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "clear",
                            tint = PrimaryColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "search") },
                hintText = stringResource(R.string.search_users),

                )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (val uiState = viewModel.userListState) {
                is UserUiState.Error -> ErrorText(message = uiState.message)
                UserUiState.Loading -> CircularProgressIndicator()
                is UserUiState.Success -> SuccessUi(
                    userList = uiState.usersList, onTap = onUserTap
                )
            }
        }
    }
}

@Composable
fun SuccessUi(modifier: Modifier = Modifier, userList: List<User>, onTap: (Int) -> Unit) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(userList) { user ->
            ElevatedCard(onTap = { onTap(user.id) }) {
                Row(modifier = Modifier.padding(12.dp)) {
                    AsyncImage(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .border(
                                width = 2.dp, color = SecondaryColor, shape = RoundedCornerShape(50)
                            ),
                        model = user.imageUrl,
                        contentDescription = "user",
                        error = painterResource(R.drawable.ic_launcher_background),
                        contentScale = ContentScale.FillHeight,
                        placeholder = painterResource(R.drawable.ic_launcher_foreground)
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            user.name,
                            fontSize = 16.sp,
                            color = PrimaryColor,
                            fontWeight = FontWeight.SemiBold
                        )
                        IconText(icon = Icons.Filled.LocationOn, text = user.address.street)
                        Spacer(modifier = Modifier.height(4.dp))
                        IconText(
                            icon = Icons.Filled.Person,
                            text = "@${user.username}",
                            fontStyle = FontStyle.Italic
                        )
                    }

                }
            }
        }
    }

}

