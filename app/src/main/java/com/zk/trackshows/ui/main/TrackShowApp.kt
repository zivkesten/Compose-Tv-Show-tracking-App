package com.zk.trackshows.ui.main

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
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
    navigationConfigurations(navController, viewModel, initialScreen = AppScreens.Main.route) // Declare the navigation flows
    handleNavigationActions(viewModel, navController) // Act on navigation actions flow
}

@ExperimentalCoroutinesApi
@FlowPreview
@ExperimentalAnimationApi
@Composable
private fun navigationConfigurations(

    navController: NavHostController,
    viewModel: MainViewModel,
    initialScreen: String,
) {
    NavHost(navController, startDestination = initialScreen) {
        composable(AppScreens.Main.route) {
            MainScreen(viewModel)
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
private fun handleNavigationActions(viewModel: MainViewModel, navController: NavHostController) {
    CoroutineScope(Dispatchers.Main).launch {
        viewModel.navigateTo.collect {
            navController.navigate(it.route)
        }
    }
}