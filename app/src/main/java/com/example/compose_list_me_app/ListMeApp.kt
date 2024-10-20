package com.example.compose_list_me_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.compose_list_me_app.common.getNavItems
import com.example.compose_list_me_app.posts.presentations.CommentsScreen
import com.example.compose_list_me_app.posts.presentations.CommentsScreenObject
import com.example.compose_list_me_app.posts.presentations.PostListScreen
import com.example.compose_list_me_app.posts.presentations.PostViewModel
import com.example.compose_list_me_app.ui.theme.PrimaryColor
import com.example.compose_list_me_app.ui.theme.SecondaryColor
import com.example.compose_list_me_app.users.presentation.AlbumScreen
import com.example.compose_list_me_app.users.presentation.AlbumScreenParams
import com.example.compose_list_me_app.users.presentation.UserDetailScreen
import com.example.compose_list_me_app.users.presentation.UserListScreen
import com.example.compose_list_me_app.users.presentation.UsersViewModel
import kotlinx.serialization.Serializable

@Composable
fun ListMeApp() {

    val navController = rememberNavController()
    NavGraph(navController = navController)

}

@Composable
fun NavGraph(navController: NavHostController) {
    val usersVm: UsersViewModel = viewModel(factory = UsersViewModel.Factory)
    val postVm: PostViewModel = viewModel(factory = PostViewModel.Factory)
    val navVm: NavViewModel = viewModel(factory = NavViewModel.Factory)
    NavHost(navController = navController, startDestination = NavPage) {

        composable<NavPage> {
            NavPage(
                onUserTap = {
                    navController.navigate(UserDetailScreen(it))
                },
                onPostTap = { navController.navigate(CommentsScreenObject(it)) },
                usersVm = usersVm,
                postVm = postVm,
                navVm = navVm
            )
        }
        composable<UserDetailScreen> {
            val args = it.toRoute<UserDetailScreen>()
            UserDetailScreen(
                userViewModel = usersVm,
                navigateBack = { navController.popBackStack() },
                userId = args.id,
                onAlbumTap = { albumId -> navController.navigate(AlbumScreenParams(albumId)) },
                postList = postVm.getUserPosts(args.id)
            )
        }
        composable<AlbumScreenParams> {
            val args = it.toRoute<AlbumScreenParams>()
            AlbumScreen(albumId = args.albumId, viewModel = usersVm, navigateBack = {
                navController.popBackStack()
            })
        }
        composable<CommentsScreenObject> {
            val args = it.toRoute<CommentsScreenObject>()
            CommentsScreen(
                postId = args.postId, onPop = {
                    navController.popBackStack()
                }, postViewModel = postVm
            )
        }
    }
}


@Serializable
object NavPage

@Composable
fun NavPage(
    modifier: Modifier = Modifier,
    onUserTap: (Int) -> Unit,
    onPostTap: (Int) -> Unit,
    usersVm: UsersViewModel,
    postVm: PostViewModel,
    navVm: NavViewModel
) {

    val context = LocalContext.current
    val selectedIndex = navVm.selectedIndex.intValue

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        bottomBar = {
            NavigationBar {
                getNavItems(context).forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = PrimaryColor,
                            indicatorColor = PrimaryColor,
                        ),
                        selected = selectedIndex == index,
                        label = { Text(item.label) },
                        icon = { Icon(item.icon, contentDescription = "icon") },
                        onClick = { navVm.updateSelectedIndex(index) },
                    )
                }
            }
        }) {
        when (selectedIndex) {
            0 -> UserListScreen(
                modifier = modifier.padding(
                    bottom = it.calculateBottomPadding()
                ), onUserTap = onUserTap, viewModel = usersVm

            )

            1 -> PostListScreen(
                modifier = modifier.padding(
                    bottom = it.calculateBottomPadding()
                ), navigateCommentsScreen = onPostTap, viewModel = postVm
            )
        }
    }
}
