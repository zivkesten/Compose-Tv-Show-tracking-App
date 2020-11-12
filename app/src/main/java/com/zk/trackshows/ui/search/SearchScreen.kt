package com.zk.trackshows.ui.search

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.zk.trackshows.components.NetworkImageComponentPicasso
import com.zk.trackshows.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun SearchScreen(viewModel: MainViewModel) {
    val shows = viewModel.shows

    Column() {
        TopAppBar(title = {
            Text("Michelle's movie app", fontFamily = FontFamily.Serif)
        }, actions = {
            Modifier.padding(8.dp)
            Icon(Icons.Default.ArrowBack)
        }, backgroundColor = Color.Black
        )

        ScrollableColumn(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
        ) {
            StaggeredVerticalGrid(
                maxColumnWidth = 220.dp,
                modifier = Modifier.padding(4.dp)
            ) {
                shows?.forEach { show ->
                    Card() {
                        Column() {
                            NetworkImageComponentPicasso(
                                    url = show.poster_path,
                                    modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .clickable(onClick = { viewModel.tapShowEvent(show.id)})
                            )
                        }
                    }
                }
            }
        }
    }
}

