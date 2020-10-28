package com.zk.trackshows.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.zk.trackshows.R
import com.zk.trackshows.components.BottomNavigationOnlySelectedLabelComponent
import com.zk.trackshows.ui.mainScreens.DiscoverScreen
import com.zk.trackshows.ui.mainScreens.MyShows
import com.zk.trackshows.ui.mainScreens.WatchList

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    selectShow: (Int) -> Unit,
    tapSearch: () -> Unit,
    selectedNavigationItem: NavigationItems,
    setSelectedNavigationItem: (NavigationItems) -> Unit
) {
    val fabShape = RoundedCornerShape(50)
    Scaffold(
        topBar = {
            TopAppBar( title = {
                Text("Michelle's movie app", fontFamily = FontFamily.Serif)
            }, actions = {
                Icon(Icons.Default.Search, modifier = Modifier.padding(8.dp))
                Icon(Icons.Default.Sort, modifier = Modifier.padding(8.dp))
                Icon(Icons.Default.Settings, modifier = Modifier.padding(8.dp))
            }, backgroundColor = Color.Transparent
            )
        },
        bottomBar = {
            // We specify the shape of the FAB bu passing a shape composable (fabShape) as a
            // parameter to cutoutShape property of the BottomAppBar. It automatically creates a
            // cutout in the BottomAppBar based on the shape of the Floating Action Button.
            BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                BottomNavigationOnlySelectedLabelComponent(
                    selectedNavigationItem,
                    setSelectedNavigationItem
                )
            }
        },
        floatingActionButton = {
            // Only Watchlist screen should include FAB
            if (selectedNavigationItem == NavigationItems.WATCHLIST) {
                FloatingActionButton(
                    onClick = {},
                    // We specify the same shape that we passed as the cutoutShape above.
                    shape = fabShape,
                    // We use the secondary color from the current theme. It uses the defaults when
                    // you don't specify a theme (this example doesn't specify a theme either hence
                    // it will just use defaults. Look at DarkModeActivity if you want to see an
                    // example of using themes.
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    IconButton(onClick = {}) {
                        Icon(asset = Icons.Filled.Add)
                    }
                }
            }

        },
        floatingActionButtonPosition = FabPosition.End,
        bodyContent = { padding ->
            when (selectedNavigationItem) {
                NavigationItems.WATCHLIST -> WatchList(padding = padding, selectShow = selectShow)
                NavigationItems.MY_SHOWS -> MyShows(padding = padding, selectShow = selectShow)
                NavigationItems.DISCOVER -> DiscoverScreen(padding = padding, selectShow = selectShow, tapSearch = tapSearch)
            }
        }
    )
}

enum class NavigationItems(
    @StringRes val title: Int,
    val icon: VectorAsset
) {
    WATCHLIST(R.string.menu_watchlist, Icons.Filled.Home),
    MY_SHOWS(R.string.menu_my_shows, Icons.Filled.Radio),
    DISCOVER(R.string.menu_discover, Icons.Filled.LibraryAdd),
    STATISTICS(R.string.menu_statistics, Icons.Filled.Gamepad),
}

