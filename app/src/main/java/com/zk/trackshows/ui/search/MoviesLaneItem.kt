package com.zk.trackshows.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.zk.trackshows.model.Show
import com.zk.trackshows.ui.main.MainViewModel
import com.zk.trackshows.ui.theme.typography
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun MoviesLaneItem(shows: List<Show>, title: String = "", viewModel: MainViewModel) {

    if (title.isNotEmpty()) {
        Text(
            text = title,
            style = typography.h6,
            modifier = Modifier.padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 24.dp)
        )
    }
    LazyRowFor(items = shows) { show ->
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