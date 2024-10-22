package com.example.compose_list_me_app.common.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.compose_list_me_app.ui.theme.PrimaryColor
import com.example.compose_list_me_app.ui.theme.SecondaryColor


@Composable
fun IconText(
    icon: ImageVector, text: String, fontStyle: FontStyle = FontStyle.Normal
) {
    Row {
        Icon(
            icon, contentDescription = "location", tint = SecondaryColor
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = PrimaryColor,
            fontWeight = FontWeight(300),
            fontStyle = fontStyle
        )
    }
}