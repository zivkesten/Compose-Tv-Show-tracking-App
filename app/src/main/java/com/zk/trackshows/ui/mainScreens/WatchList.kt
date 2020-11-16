package com.zk.trackshows.ui.mainScreens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.zk.trackshows.AnimatedBottomNavigationTransition
import com.zk.trackshows.R
import com.zk.trackshows.bottomNavigationEnterTransitions
import com.zk.trackshows.bottomNavigationExitTransitions
import com.zk.trackshows.components.ShowCard
import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.Result
import com.zk.trackshows.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun WatchList(viewModel: MainViewModel) {
    AnimatedBottomNavigationTransition(
        enter = bottomNavigationEnterTransitions(),
        exit = bottomNavigationExitTransitions()
    ) {
        WatchListContent(viewModel)
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
private fun WatchListContent(viewModel: MainViewModel) {
    val showsState = viewModel.watchListState.collectAsState()

    when (val data = showsState.value.data) {
        is Result.Loading -> { LoadingWidget() }
        is Result.Error -> { ErrorWidget(data) }
        is Result.Success -> {
            if (data.data.isEmpty()) EmptyListWidget() else ListWidget(data, viewModel)
        }
    }
}

@Composable
fun EmptyListWidget() {
    Box(alignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = ContextAmbient.current
            val customView = remember { LottieAnimationView(context) }
            // Adds view to Compose
            AndroidView({ customView },
                modifier = Modifier.size(150.dp)
            ) { view ->
                // View's been inflated - add logic here if necessary
                with(view) {
                    setAnimation(R.raw.empty_list)
                    playAnimation()
                    repeatMode = LottieDrawable.REVERSE
                }
            }
            Text(text = "Seems like you have no shows saved")
        }
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
private fun ListWidget(
    data: Result.Success<List<Show>>,
    viewModel: MainViewModel,
) {
    // ScrollableColumn is a composable that adds the ability to scroll through the
    // child views
    ScrollableColumn {
        // Column is a composable that places its children in a vertical sequence. You
        // can think of it similar to a LinearLayout with the vertical orientation.
        Column {
            data.data.forEach { show ->
                // Card composable is a predefined composable that is meant to represent
                // the card surface as specified by the Material Design specification. We
                // also configure it to have rounded corners and apply a modifier.
                ShowCard(
                    show, modifier = Modifier.fillMaxWidth()
                        .clickable(onClick = {
                            viewModel.tapShowEvent(show.id)
                        })
                )
            }
        }
    }
}

@Composable
private fun ErrorWidget(data: Result.Error) {
    Box(
        alignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(asset = Icons.Filled.HourglassEmpty)
            Text(
                text = data.exception.localizedMessage ?: "Error",
                color = Color.Red,
                fontSize = 30.sp)
        }
    }
}

@Composable
private fun LoadingWidget() {
    Box(
        alignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            strokeWidth = 5.dp,
            modifier = Modifier
                .size(200.dp)
                .padding(50.dp)
        )
    }
}
