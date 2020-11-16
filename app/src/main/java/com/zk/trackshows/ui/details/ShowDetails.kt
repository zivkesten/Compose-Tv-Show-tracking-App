package com.zk.trackshows.ui.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zk.trackshows.extensions.whenNotNull
import com.zk.trackshows.ui.main.MainViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun ShowDetails(viewModel: MainViewModel) {

    val show = viewModel.showState.value.show
    whenNotNull(show) { currentShow ->
        CoilImage(
                data = "https://image.tmdb.org/t/p/w500/${currentShow.poster_path}",
                modifier = Modifier.fillMaxSize()
        )
    }
}
