package com.zk.trackshows.ui.mainScreens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zk.trackshows.AnimatedBottomNavigationTransition
import com.zk.trackshows.ui.components.LazyPagingRowWithPagingData
import com.zk.trackshows.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun DiscoverScreen(viewModel: MainViewModel) {

    AnimatedBottomNavigationTransition(
        enter = /*bottomNavigationEnterTransitions()*/fadeIn(initialAlpha = 0.3f),
        exit = fadeOut()
    ) {
        DiscoverScreenContent(viewModel)
    }
}


@ExperimentalCoroutinesApi
@FlowPreview
@Composable
private fun DiscoverScreenContent(
    viewModel: MainViewModel
) {
    ScrollableColumn {
        Column {
            SearchBox(viewModel::tapSearch)
            LazyPagingRowWithPagingData(
                title = "Top Rated",
                viewModel::tapShowEvent,
                viewModel.topRatedShowsPagedData
            )
            LazyPagingRowWithPagingData(
                title = "Popular shows",
                viewModel::tapShowEvent,
                viewModel.popularShowsPagedData
            )
            LazyPagingRowWithPagingData(
                title = "Trending Shows",
                viewModel::tapShowEvent,
                viewModel.trendingShowsPagedData
            )
        }
    }
}

@Composable
fun SearchBox(tapSearch: () -> Unit) {
    Box(
        modifier = Modifier
            .background(Color.DarkGray, shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .height(40.dp)
            .padding(4.dp)
            .clickable(onClick = tapSearch)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Outlined.Search, modifier = Modifier.padding(4.dp))
            Text(text = "Tap to search", style = TextStyle(Color.Gray, 10.sp))
        }
    }
}
