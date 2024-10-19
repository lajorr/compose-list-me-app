package com.example.compose_list_me_app.posts.presentations

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_list_me_app.R
import com.example.compose_list_me_app.common.MyAppBar
import com.example.compose_list_me_app.ui.theme.BackgroundColor
import com.example.compose_list_me_app.ui.theme.PrimaryColor
import com.example.compose_list_me_app.ui.theme.SecondaryColor
import java.util.Locale


@Composable
fun PostListScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MyAppBar(title = stringResource(R.string.posts), canPop = false)
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(2) {
                PostTile()
            }
        }
    }
}

@Composable
fun PostTile(modifier: Modifier = Modifier, ) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(100.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
        .clickable {

//            onTap(user.id)
        }
        .padding(12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
//                .height(intrinsicSize = IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(PrimaryColor)
                    .border(2.dp, SecondaryColor, RoundedCornerShape(50)),
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = "1", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold
                )
            }
            Column(modifier = Modifier.fillMaxHeight()) {
                Text(
                    text = "title".replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "body", fontSize = 16.sp, overflow = TextOverflow.Ellipsis, maxLines = 2
                )
            }
        }

    }
}