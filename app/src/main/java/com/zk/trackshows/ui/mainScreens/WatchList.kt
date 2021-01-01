package com.zk.trackshows.ui.mainScreens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.zk.trackshows.AnimatedBottomNavigationTransition
import com.zk.trackshows.common.InfoLogger.logMessage
import com.zk.trackshows.components.ShowCard
import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.ui.main.MainScreenEvent
import com.zk.trackshows.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun WatchList(viewModel: MainViewModel) {
    AnimatedBottomNavigationTransition(
        enter = /*bottomNavigationEnterTransitions()*/fadeIn(initialAlpha = 0.3f),
        exit = fadeOut()
    ) {
        WatchListContent(viewModel)
    }
}


@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun WatchListContent(
    viewModel: MainViewModel,
) {
    viewModel.onEvent(MainScreenEvent.ScreenLoad)
    val watchListState = viewModel.watchListState.collectAsState().value

    logMessage("watchListState.watchedShows: ${watchListState.watchedShows?.size}")
    watchListState.loading?.let { loading -> if (loading) CircularProgressIndicator() }
    watchListState.error?.let { error -> ErrorWidget(error) }
    watchListState.watchedShows?.let { shows ->
        if (shows.isEmpty()) {
            Text("Empty list")
        } else {
            ListWidget(viewModel, shows)
        }
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
private fun ListWidget(viewModel: MainViewModel, watchedShows: List<Show>) {
    LazyColumn {
        items(watchedShows) { show ->
            ShowCard(show = show, modifier = Modifier
                .clickable(onClick = {
                    viewModel.tapShowEvent(show)
                })
            )
        }
    }
}

@Composable
private fun ErrorWidget(data: Throwable) {
    Box(

        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(imageVector = Icons.Filled.HourglassEmpty)
            Text(
                text = data.localizedMessage ?: "Error",
                color = Color.Red,
                fontSize = 30.sp)
        }
    }
}





