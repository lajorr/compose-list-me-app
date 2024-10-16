package com.example.compose_list_me_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose_list_me_app.common.getNavItems
import com.example.compose_list_me_app.posts.presentations.PostListScreen
import com.example.compose_list_me_app.users.presentation.UserListScreen
import kotlinx.serialization.Serializable

@Composable
fun ListMeApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    NavGraph(navController = navController)

}

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavPage) {

        composable<NavPage> {
            NavPage()
        }

    }
}


@Serializable
object NavPage

@Composable
fun NavPage(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        bottomBar = {
            NavigationBar {
                getNavItems(context).forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        label = { Text(item.label) },
                        icon = { Icon(item.icon, contentDescription = "icon") },
                        onClick = { selectedIndex = index }
                    )
                }
            }
        }) {
        PageList(modifier = Modifier.padding(it), index = selectedIndex)
    }
}

@Composable
fun PageList(modifier: Modifier = Modifier, index: Int) {

    when (index) {
        0 -> UserListScreen(modifier)
        1 -> PostListScreen(modifier)
    }
}