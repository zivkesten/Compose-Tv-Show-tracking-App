package com.zk.trackshows.ui.search

import androidx.compose.material.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.zk.trackshows.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun SearchScreen(viewModel: MainViewModel) {
    Column {
        TopAppBar(title = {
            Text("Michelle's movie app", fontFamily = FontFamily.Serif)
        }, actions = {
            Modifier.padding(8.dp)
            Icon(Icons.Default.ArrowBack)
        }, backgroundColor = Color.Black
        )
        MovieTrendingScreen(viewModel = viewModel)
    }
}

