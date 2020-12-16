package com.zk.trackshows.components

import androidx.compose.material.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zk.trackshows.model.Show
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun ShowCard(show: Show, modifier: Modifier) {

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        CoilImage(
            data = "https://image.tmdb.org/t/p/w500/${show.poster_path}",
            modifier = modifier
                .then(Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(10.dp))
                ),
            loading = { CircularProgressIndicator() },
            fadeIn = true
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                    text = show.name,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp)
            )
            Text(
                    text = show.original_name,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.SansSerif,
                    //modifier = Modifier.padding(4.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                LinearProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        backgroundColor = MaterialTheme.colors.secondary,
                        progress = 0.3f,
                        modifier = Modifier.height(12.dp)
                )
                Text(
                        maxLines = 1,
                        text = "66/73",
                        fontSize = 10.sp,
                        modifier = Modifier.padding(4.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {

                Button(
                        onClick = {}) {
                    Text(text = "Episode Info")
                }
                Text(
                        text = "7 left",
                        fontSize = 10.sp,
                        modifier = Modifier.padding(4.dp)
                )
            }

        }
    }
}