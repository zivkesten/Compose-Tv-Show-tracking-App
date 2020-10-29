package com.zk.trackshows.ui.details

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.zk.trackshows.components.NetworkImageComponentPicasso
import com.zk.trackshows.model.Show
import com.zk.trackshows.ui.main.MainViewModel

@Composable
fun ShowDetails(
    viewModel: MainViewModel
) {
//    val details: Show? by viewModel.showDetails.observeAsState()
//    details?.let {
//        Log.d("Zivi", "detailesScreen")
//        NetworkImageComponentPicasso(url = it.poster_path, modifier = Modifier.fillMaxSize())
//    }
}
