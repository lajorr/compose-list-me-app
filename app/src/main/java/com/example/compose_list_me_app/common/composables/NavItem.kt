package com.example.compose_list_me_app.common.composables

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.compose_list_me_app.R

data class NavItem(
    val label: String,
    val icon: ImageVector
)

fun getNavItems(context: Context): List<NavItem> {
    return listOf(
        NavItem(
            label = context.getString(R.string.users),
            icon = Icons.Filled.Person
        ),
        NavItem(
            label = context.getString(R.string.posts),
            icon = Icons.Filled.Info
        ),
    )
}