package com.zk.trackshows.ui.details

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zk.trackshows.components.NetworkImageComponentPicasso
import com.zk.trackshows.extensions.whenNotNull
import com.zk.trackshows.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun ShowDetails(viewModel: MainViewModel) {

    val show = viewModel.state.value.show
    Log.i("Zivi", "show: ${show?.name}")
    whenNotNull(show) { currentShow ->
        NetworkImageComponentPicasso(
                url = currentShow.poster_path,
                modifier = Modifier.fillMaxSize()
        )
    }
}
