package com.zk.trackshows.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.zk.trackshows.R
import com.zk.trackshows.extensions.whenNotNull
import com.zk.trackshows.ui.main.MainViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun HorizontalScrollableComponent(viewModel: MainViewModel) {
    val showList = viewModel.currentShowList()
    whenNotNull(showList) { shows ->
        LazyRowFor(items = shows) {
            shows.forEach { show ->
                Card(
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(16.dp)
                        .clickable(onClick = { viewModel.tapShowEvent(showId = show.id) })
                ) {
                    CoilImage(
                        data = "https://image.tmdb.org/t/p/w500/${show.poster_path}",
                        modifier = Modifier.padding(4.dp),
                        loading = {
                            Box(Modifier.fillMaxWidth()) {
                                CircularProgressIndicator(Modifier.align(Alignment.Center))
                            }
                        },
                        error = {
                            Image(asset = imageResource(R.drawable.ic_launcher_foreground))
                        }
                    )
                }
            }
        }
    }
}