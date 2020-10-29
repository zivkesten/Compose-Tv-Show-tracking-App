package com.zk.trackshows.ui.search

import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.zk.trackshows.components.NetworkImageComponentPicasso
import com.zk.trackshows.components.ShowCard
import com.zk.trackshows.ui.mainScreens.popularShowsGenerator
import com.zk.trackshows.ui.mainScreens.showJson

@Composable
fun SearchScreen() {
    val shows = popularShowsGenerator(showJson())?.shows

    Column() {
        TopAppBar(title = {
            Text("Michelle's movie app", fontFamily = FontFamily.Serif)
        }, actions = {
            Icon(Icons.Default.ArrowBack, modifier = Modifier.padding(8.dp))
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
                    //HomePoster(poster = poster, selectPoster = selectPoster)
                    Card() {
                        Column() {
                            NetworkImageComponentPicasso(
                                url = show.poster_path,
                                modifier = Modifier.clip(
                                RoundedCornerShape(10.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}

