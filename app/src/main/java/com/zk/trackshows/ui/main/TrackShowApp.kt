package com.zk.trackshows.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.zk.trackshows.ui.details.ShowDetails
import com.zk.trackshows.ui.search.SearchScreen
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

sealed class AppScreens(val route: String) {
    object Main : AppScreens("Main")
    object Details : AppScreens("Details")
    object Search : AppScreens("Search")
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