package com.zk.trackshows.ui.main

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.foundation.Text
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
import com.zk.trackshows.ui.mainScreens.DiscoverScreen
import com.zk.trackshows.ui.mainScreens.MyShows
import com.zk.trackshows.ui.mainScreens.WatchList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int) {
    object WatchList : BottomNavigationScreens("watchList", R.string.watchlist_route)
    object MyShows : BottomNavigationScreens("myShows", R.string.my_shows_screen_route)
    object Discover : BottomNavigationScreens("discover", R.string.discover_screen_route)
    object Statistics : BottomNavigationScreens("statistics", R.string.statistics_screen_route)
}

@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun MainScreen(viewModel: MainViewModel,
               selectShow: (Int) -> Unit,
               tapSearch: () -> Unit)
{
    val navController = rememberNavController()
    val items = listOf(
            BottomNavigationScreens.WatchList,
            BottomNavigationScreens.MyShows,
            BottomNavigationScreens.Discover,
            BottomNavigationScreens.Statistics
    )
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Michelle's movie app", fontFamily = FontFamily.Serif)
        }, actions = {
            Icon(Icons.Default.Search, modifier = Modifier.padding(8.dp))
            Icon(Icons.Default.Sort, modifier = Modifier.padding(8.dp))
            Icon(Icons.Default.Settings, modifier = Modifier.padding(8.dp))
        }, backgroundColor = Color.Transparent
        )
    },
            bottomBar = {
                BottomNavigation {
                    val currentRoute = currentRoute(navController, KEY_ROUTE)
                    items.forEach { screen ->
                        BottomNavigationItem(
                                icon = {
                                    when (screen) {
                                        is BottomNavigationScreens.WatchList -> Icon(Icons.Filled.Terrain)
                                        is BottomNavigationScreens.MyShows -> Icon(Icons.Filled.Satellite)
                                        is BottomNavigationScreens.Discover -> Icon(Icons.Filled.LocalSee)
                                        is BottomNavigationScreens.Statistics -> Icon(Icons.Filled.ChargingStation)
                                    }
                                },
                                label = { Text(stringResource(id = screen.resourceId)) },
                                selected = currentRoute == screen.route,
                                alwaysShowLabels = false,
                                onClick = {
                                    // This if check gives us a "singleTop" behavior where we do not create a
                                    // second instance of the composable if we are already on that destination
                                    if (currentRoute != screen.route) {
                                        Log.d("Zivi", "navigate to: ${screen.route}")
                                        navController.navigate(screen.route)
                                    }
                                }
                        )
                    }
                }
            }, floatingActionButton = {
        val currentRoute = currentRoute(navController, KEY_ROUTE)
        // Only Watchlist screen should include FAB
        val isWatchList = currentRoute == BottomNavigationScreens.WatchList.route
        AnimatedVisibility(visible = isWatchList) {
            FloatingActionButton(
                    onClick = {},
                    backgroundColor = MaterialTheme.colors.primary
            ) {
                IconButton(onClick = {}) {
                    Icon(asset = Icons.Filled.Add)
                }
            }
        }
    },
            floatingActionButtonPosition = FabPosition.End
    ) {
        NavHost(navController, startDestination = BottomNavigationScreens.WatchList.route) {
            composable(BottomNavigationScreens.WatchList.route) {
                  WatchList(
                          viewModel
                  )
            }
            composable(BottomNavigationScreens.MyShows.route) {
                MyShows(
                        navController,
                        selectShow)
            }
            composable(BottomNavigationScreens.Discover.route) {
                DiscoverScreen(
                        selectShow,
                        tapSearch
                )
            }
            composable(BottomNavigationScreens.Statistics.route) {
                WatchList(
                        viewModel
                )
            }
        }
    }
}

@Composable
private fun currentRoute(
        navController: NavHostController,
        routeKey: String
): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(routeKey)
}