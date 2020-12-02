package com.zk.trackshows.ui.mainScreens

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.zk.trackshows.model.Show
import androidx.paging.compose.items
import com.zk.trackshows.components.LazyPagingRow

import com.zk.trackshows.repository.Result
import com.zk.trackshows.repository.network.api.ApiPagingSource
import com.zk.trackshows.repository.network.api.TvShowsService
import com.zk.trackshows.ui.main.MainViewModel
import com.zk.trackshows.ui.search.MoviesLaneItem
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun DiscoverScreen(viewModel: MainViewModel, service: TvShowsService) {

        ScrollableColumn() {
            Column() {
                SearchBox(viewModel::tapSearch)
                LazyPagingRow(dataSource = service::fetchPagedTopRatedShows,
                    title = "Top Rated",
                    viewModel = viewModel)
                LazyPagingRow(dataSource = service::fetchPagedTrendingTVShows,
                    title = "Trending this week",
                    viewModel = viewModel)
                LazyPagingRow(dataSource = service::fetchPagedPopularShows,
                    title = "Popular shows",
                    viewModel = viewModel)
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
            Icon(asset = Icons.Outlined.Search, modifier = Modifier.padding(4.dp))
            Text(text = "Tap to search", style = TextStyle(Color.Gray, 10.sp))
        }
    }
}
