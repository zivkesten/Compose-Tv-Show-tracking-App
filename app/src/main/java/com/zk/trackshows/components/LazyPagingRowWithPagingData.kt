package com.zk.trackshows.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.ui.theme.typography
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun LazyPagingRowWithPagingData(
    title: String,
    tapAction: (Show) -> Unit,
    loadStream: Flow<PagingData<Show>>
) {

    val lazyPagingItems: LazyPagingItems<Show> = loadStream.collectAsLazyPagingItems()

    Column {
        RowTitle(title)
        PagingRow(lazyPagingItems, tapAction)
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
    tapAction: (Show) -> Unit
) {
    LazyRow {
        //logMessage("lazyPagingItems: ${lazyPagingItems.itemCount}")
        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                PlaceHolder()
            }
        }
        items(lazyPagingItems) { show ->
            if (show != null) {
                CoilImage(
                    data = "https://image.tmdb.org/t/p/original/${show.poster_path}",
                    modifier = Modifier
                        .preferredWidth(190.dp)
                        .preferredHeight(300.dp)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = { tapAction.invoke(show) }),
                    contentScale = ContentScale.Crop,
                    loading = { PlaceHolder() },
                    fadeIn = true
                )
            }
        }

        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                PlaceHolder()
            }
        }
    }
}

@Composable
private fun PlaceHolder() {
    Box(modifier = Modifier
        .preferredWidth(190.dp)
        .preferredHeight(300.dp)
        .padding(12.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(color = Color.LightGray)
    )
}