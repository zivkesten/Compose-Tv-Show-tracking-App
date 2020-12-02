package com.zk.trackshows.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.network.api.ApiPagingSource
import com.zk.trackshows.repository.network.api.TvShowResponse
import com.zk.trackshows.ui.main.MainViewModel
import com.zk.trackshows.ui.theme.typography
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun LazyPagingRow(
    dataSource: suspend (Int) -> TvShowResponse,
    title: String,
    viewModel: MainViewModel,
) {
    val charactersPaging = remember {
        ApiPagingSource(dataSource)
    }
    val pager = remember {
        Pager(
            PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
            )
        ) {
            charactersPaging
        }
    }

    val lazyPagingItems: LazyPagingItems<Show> = pager.flow.collectAsLazyPagingItems()

    Column {
        RowTitle(title)
        PagingRow(lazyPagingItems, viewModel)
    }
}

@Composable
private fun RowTitle(title: String) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            style = typography.h6,
            modifier = Modifier.padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 24.dp)
        )
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
private fun PagingRow(
    lazyPagingItems: LazyPagingItems<Show>,
    viewModel: MainViewModel,
) {
    LazyRow {
        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                CircularProgressIndicator()
            }
        }
        items(lazyPagingItems) { show ->
            if (show != null) {
                CoilImage(
                    data = "https://image.tmdb.org/t/p/w500/${show.poster_path}",
                    modifier = Modifier
                        .preferredWidth(190.dp)
                        .preferredHeight(300.dp)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = { viewModel.tapShowEvent(show = show) }),
                    contentScale = ContentScale.Crop
                )
            }
        }

        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }
    }
}