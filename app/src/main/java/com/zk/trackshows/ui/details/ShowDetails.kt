package com.zk.trackshows.ui.details

import androidx.compose.animation.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zk.trackshows.model.Show
import com.zk.trackshows.ui.theme.typography
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun ShowDetails(viewModel: DetailViewModel, show: Show?) {

    //logMessage("the show: ${show?.name}")
    show?.let { viewModel.onEvent(Event.ScreenLoad(it)) }
    Box(modifier = Modifier.fillMaxSize().background(Color.Green)) {

        Column {

            ShowDetailContent(viewModel, show = show)
        }

        TopAppBar(
            title = {},
            actions = {
                Icon(Icons.Default.Share, modifier = Modifier.padding(8.dp))
            },
            navigationIcon = {
                Icon(Icons.Default.ArrowBack, modifier = Modifier.padding(8.dp))
            },
            contentColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }
}
@ExperimentalCoroutinesApi
@Composable
fun ShowDetailContent(viewModel: DetailViewModel, show: Show?) {
    val expand = remember { mutableStateOf(false) }
    val state = viewModel.detailScreenState.collectAsState().value
    //val viewModel: MainViewModel = viewModel()
    //var dominantColors = listOf(graySurface, Color.Black)

//    if (imageId != 0) {
//        var currentBitmap = imageResource(id = imageId).asAndroidBitmap()
//        val swatch = generateDominantColorState(currentBitmap)
//        dominantColors = listOf(Color(swatch.rgb), Color.Black)
//    }

    ScrollableColumn(
        modifier = Modifier.background(Color.LightGray)
            .padding(
                animate(
                    if (expand.value) 1.dp else 120.dp,
                    tween(350)
                )
            )
    ) {
        show?.let { show ->
            CoilImage(
                data = "https://image.tmdb.org/t/p/w500/${show.poster_path}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .preferredHeight(
                        600.dp
                    ).fillMaxWidth(),
                onRequestCompleted = {
                    expand.value = true
                }
            )
            Column(modifier = Modifier.background(MaterialTheme.colors.surface)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = show.name,
                        modifier = Modifier.padding(8.dp),
                        style = typography.h6
                    )
                    FloatingActionButton(onClick = { viewModel.onEvent(Event.TapAddToWatchList(show)) }) {

                        val icon = if (state.isInWatchList) Icons.Default.Remove else Icons.Default.Add
                        Icon(
                            imageVector = icon,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
                //GenreSection(viewModel, movie.genre_ids)
                Text(
                    text = "First aired: ${show.first_air_date}",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = typography.h6.copy(fontSize = 12.sp)
                )
                Text(
                    text = "PG13  •  ${show.vote_average}/10",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = typography.h6.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = show.overview,
                    modifier = Modifier
                        .padding(8.dp),
                    style = typography.subtitle2
                )
                Spacer(modifier = Modifier.height(20.dp))
                //SimilarMoviesSection(movie, viewModel)
                Spacer(modifier = Modifier.height(50.dp))
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Get Tickets", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}
