package com.zk.trackshows.ui.main

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.zk.trackshows.R
import com.zk.trackshows.common.InfoLogger
import com.zk.trackshows.data.network.api.TvShowsService
import com.zk.trackshows.ui.mainScreens.DiscoverScreen
import com.zk.trackshows.ui.mainScreens.WatchList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int) {
    object WatchList : BottomNavigationScreens("watchList", R.string.watchlist_route)
    object Discover : BottomNavigationScreens("discover", R.string.discover_screen_route)
}

@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.WatchList,
        BottomNavigationScreens.Discover
    )
    Scaffold(
        topBar = { TrackShowsTopBar() },
        bottomBar = {
            TrackShowsBottomNavigation(navController, bottomNavigationItems) {
                handleMainScreenInteractionEvents(navController, it)
            }
        },
        floatingActionButton = { TrackShowsFloatingActionButton(navController) },
        floatingActionButtonPosition = FabPosition.End
    ) {
        MainScreenNavigationConfigurations(navController, viewModel)
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@ExperimentalAnimationApi
@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(navController, startDestination = BottomNavigationScreens.Discover.route) {
        composable(BottomNavigationScreens.WatchList.route) {
            WatchList(viewModel)
        }
        composable(BottomNavigationScreens.Discover.route) {
            DiscoverScreen(viewModel)
        }
    }
}

@Composable
private fun TrackShowsTopBar() {
    TopAppBar(title = {
        Text("Michelle's movie app", fontFamily = FontFamily.Serif)
    }, actions = {
        Icon(Icons.Default.Search, modifier = Modifier.padding(8.dp))
        Icon(Icons.Default.Sort, modifier = Modifier.padding(8.dp))
        Icon(Icons.Default.Settings, modifier = Modifier.padding(8.dp))
    }, backgroundColor = Color.Transparent
    )
}

@ExperimentalAnimationApi
@Composable
private fun TrackShowsFloatingActionButton(navController: NavHostController) {
    val currentRoute = currentRoute(navController)
    // Only Watchlist screen should include FAB
    val isWatchList = currentRoute == BottomNavigationScreens.WatchList.route
    AnimatedVisibility(visible = isWatchList) {
        FloatingActionButton(
            onClick = {},
            backgroundColor = MaterialTheme.colors.primary
        ) {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.Add)
            }
        }
    }
}

@Composable
private fun TrackShowsBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>,
    mainScreenInteractionEvents: (MainScreenInteractionEvents) -> Unit
) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    when (screen) {
                        is BottomNavigationScreens.WatchList -> Icon(Icons.Filled.Terrain)
                        is BottomNavigationScreens.Discover -> Icon(Icons.Filled.LocalSee)
                    }
                },
                label = { stringResource(id = screen.resourceId) },
                selected = currentRoute == screen.route,
                alwaysShowLabels = false,
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {
                        mainScreenInteractionEvents(MainScreenInteractionEvents.NavigateTo(screen.route))
                    }
                }
            )
        }
    }
}

@Composable
private fun currentRoute(
    navController: NavHostController
): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}

fun handleMainScreenInteractionEvents(
    navController: NavHostController,
    interactionEvents: MainScreenInteractionEvents,
) {
    when (interactionEvents) {
        is MainScreenInteractionEvents.NavigateTo -> {
            navController.navigate(interactionEvents.route)
        }
        is MainScreenInteractionEvents.AddToMyWatchlist -> {
            InfoLogger.logMessage("AddToMyWatchlist clicked")
        }
        is MainScreenInteractionEvents.RemoveFromMyWatchlist -> {
            InfoLogger.logMessage("RemoveFromMyWatchlist clicked")
        }
    }
}
