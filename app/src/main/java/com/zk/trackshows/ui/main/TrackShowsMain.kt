package com.zk.trackshows.ui.main

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import com.zk.trackshows.ui.details.ShowDetails
import com.zk.trackshows.ui.mainScreens.WatchList
import com.zk.trackshows.ui.navigation.BackDispatcherAmbient
import com.zk.trackshows.ui.navigation.Navigator
import com.zk.trackshows.ui.navigation.detinations.Actions
import com.zk.trackshows.ui.navigation.detinations.Destinations
import com.zk.trackshows.ui.search.SearchScreen

@Composable
fun TrackShowsMain(viewModel: MainViewModel, backDispatcher: OnBackPressedDispatcher) {

    val navigator: Navigator<Destinations> = rememberSavedInstanceState(
        saver = Navigator.saver(backDispatcher)
    ) {
        Navigator(Destinations.Home, backDispatcher)
    }
    val actions = remember(navigator) { Actions(navigator) }
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(NavigationItems.WATCHLIST) }

    Providers(BackDispatcherAmbient provides backDispatcher) {
        Crossfade(navigator.current) { destination ->
            when (destination) {
                Destinations.Home -> MainScreen(
                    viewModel = viewModel,
                    selectShow = actions.selectShow,
                    tapSearch = actions.tapSearch,
                    selectedNavigationItem = selectedTab,
                    setSelectedNavigationItem = setSelectedTab
                )
                is Destinations.ShowDetail -> {
                    viewModel.getShow(destination.showId.toInt())
                    ShowDetails(
                        viewModel = viewModel,
                        pressOnBack = actions.pressOnBack
                    )
                }

                is Destinations.SearchScreen -> {
                    SearchScreen()
                }
            }
        }
    }
}

