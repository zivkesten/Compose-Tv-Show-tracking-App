package com.zk.trackshows.ui.mainScreens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.BabyChangingStation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.unit.dp
import com.zk.trackshows.AnimatedBottomNavigationTransition
import com.zk.trackshows.bottomNavigationEnterTransitions
import com.zk.trackshows.bottomNavigationExitTransitions
import com.zk.trackshows.components.HorizontalScrollableComponent
import com.zk.trackshows.extensions.whenNotNull
import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.mockShows
import com.zk.trackshows.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@ExperimentalAnimationApi
@Composable
fun MyShows(viewModel: MainViewModel) {
    AnimatedBottomNavigationTransition(
        enter = bottomNavigationEnterTransitions(),
        exit = bottomNavigationExitTransitions()
    ){
        // Content that needs to appear/disappear goes here:
        MyShowsContent(viewModel)
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun MyShowsContent(viewModel: MainViewModel) {
    val shows = mockShows()
    ScrollableColumn {
        Column(modifier = Modifier
            .padding(16.dp)
        ) {
            HorizontalList("Title 1", Icons.Outlined.AddBox, shows, viewModel)
            HorizontalList("Popular TV Shows", Icons.Outlined.BabyChangingStation, shows, viewModel)
        }
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun HorizontalList(title: String? = null, icon: VectorAsset? = null, shows: List<Show>?, viewModel: MainViewModel) {
    HorizontalListTitle(title, icon)
    Shows(shows, viewModel)
}

@Composable
private fun HorizontalListTitle(text: String? = null, icon: VectorAsset? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        whenNotNull(icon) { Icon(asset = it) }
        whenNotNull(text) { Text(it, modifier = Modifier.padding(4.dp)) }
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
private fun Shows(shows: List<Show>?, viewModel: MainViewModel) {
    if (shows != null) {
        HorizontalScrollableComponent(showList = shows, viewModel)
    } else {
        Text("No Shows")
    }
}