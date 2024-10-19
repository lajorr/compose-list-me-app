package com.example.compose_list_me_app.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun ErrorText(message: String) {
    Text(text = message, color = Color.Red, fontSize = 16.sp)
}

