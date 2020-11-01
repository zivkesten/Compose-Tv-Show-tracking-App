package com.zk.trackshows.ui.main

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.zk.trackshows.R
import com.zk.trackshows.ui.details.ShowDetails
import com.zk.trackshows.ui.search.SearchScreen
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

sealed class AppScreens(val route: String, @StringRes val resourceId: Int) {
    object Main : AppScreens("Main", R.string.main_screen_route)
    object Details : AppScreens("Details", R.string.details_screen_route)
    object Search : AppScreens("Search", R.string.search_screen_route)
}

@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun TrackShowsApp(viewModel: MainViewModel) {
    val navController = rememberNavController()

    handleNavigation(viewModel, navController)

    NavHost(navController, startDestination = AppScreens.Main.route) {
        composable(AppScreens.Main.route) {
            MainScreen(
                    viewModel,
                    { navController.navigate(AppScreens.Details.route) },
                    { navController.navigate(AppScreens.Search.route) }
            )
        }
        composable(
                AppScreens.Details.route + "/{showId}",
                arguments = listOf(navArgument("showId") { type = NavType.IntType })
        ) {

        }
        composable(AppScreens.Details.route) {
            ShowDetails(viewModel)
        }
        composable(AppScreens.Search.route) {
            SearchScreen(viewModel)
        }
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
private fun handleNavigation(viewModel: MainViewModel, navController: NavHostController) {
    CoroutineScope(Dispatchers.Main).launch {
        viewModel.navigateTo.collect {
            navController.navigate(it.route)
        }
    }
}