@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.compose_list_me_app.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_list_me_app.R
import com.example.compose_list_me_app.ui.theme.PrimaryColor


@Composable
fun MyAppBar(title: String, canPop: Boolean = true, navigateBack: (() -> Unit)? = null) {
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var canBackState by remember {
        mutableStateOf(true)
    }
    CustomFlexibleTopAppBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp))
                .background(PrimaryColor)
                .safeDrawingPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (canPop) Arrangement.Start else Arrangement.Center
        ) {
            if (canPop) IconButton(
                onClick = {
                    canBackState = false
                    navigateBack?.invoke()
                }, enabled = canBackState
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White
                )
            }
            Text(
                text = title,
                color = Color.White,
                fontSize = 24.sp,
            )
        }


    }
}