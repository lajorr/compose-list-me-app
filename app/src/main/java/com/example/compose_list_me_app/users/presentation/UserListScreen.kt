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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_list_me_app.ui.theme.BackgroundColor
import com.example.compose_list_me_app.ui.theme.ContainerColor2
import com.example.compose_list_me_app.ui.theme.PrimaryColor
import com.example.compose_list_me_app.ui.theme.SecondaryColor
import kotlinx.serialization.Serializable

@Serializable
object UserListScreen

@Composable
fun UserListScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp))
                    .background(PrimaryColor),
                contentAlignment = Alignment.Center,

                ) {
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Users", color = Color.White, fontSize = 24.sp)
                }

            }

            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.BottomCenter)
                .offset(y = 25.dp)
                .shadow(
                    elevation = 8.dp,
                    RoundedCornerShape(50),
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = ContainerColor2,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = ContainerColor2,
                    focusedBorderColor = PrimaryColor
                ),
                shape = RoundedCornerShape(50.dp),
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "search") },
                placeholder = { Text(text = "Search users") },
                value = "",
                onValueChange = {})
        }
        Spacer(modifier = Modifier.height(30.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            items(1) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(12.dp)
                ) {
                    Row {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(25.dp))
                                .background(Color.LightGray)
                                .border(
                                    width = 2.dp,
                                    color = SecondaryColor,
                                    shape = RoundedCornerShape(50)
                                )

                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                "User FullName",
                                fontSize = 16.sp,
                                color = PrimaryColor,
                                fontWeight = FontWeight.SemiBold
                            )
                            IconText(icon = Icons.Filled.LocationOn, text = "572 Statan NY, 12483")
                            Spacer(modifier = Modifier.height(4.dp))
                            IconText(icon = Icons.Filled.Person, text = "@lajorr")
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun IconText(modifier: Modifier = Modifier, icon: ImageVector, text: String) {
    Row {
        Icon(
            icon, contentDescription = "location", tint = SecondaryColor
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text, color = PrimaryColor, fontWeight = FontWeight(300)
        )
    }
}