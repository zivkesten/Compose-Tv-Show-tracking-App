package com.zk.trackshows.components

import android.util.Log
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zk.trackshows.model.Show

@Composable
fun HorizontalScrollableComponent(showList: List<Show>, selectShow: (Int) -> Unit) {
    // HorizontalScroller is a composable that adds the ability to scroll through the child
    // composables that are declared inside it in the horizontal direction. One caveat here is that
    // this is not optimized to recycle the views. It is more similar to [ScrollView] and should not
    // be thought of as a replacement for [RecyclerView]. We also give it a modifier.

    // You can think of Modifiers as implementations of the decorators pattern that are used to
    // modify the composable that its applied to. In this example, we ask the HorizontalScroller
    // to occupy the entire available width.
    ScrollableRow(modifier = Modifier.fillMaxWidth(), children = {
        // Row is a composable that places its children in a horizontal sequence. You
        // can think of it similar to a LinearLayout with the horizontal orientation.
        Row {
            // We iterate over each item from the personList and define what each item should
            // look like.
            for ((index, show) in showList.withIndex()) {
                // Card composable is a predefined composable that is meant to represent the card
                // surface as specified by the Material Design specification. We also configure it
                // to have rounded corners and apply a modifier.

                // You can think of Modifiers as implementations of the decorators pattern that are
                // used to modify the composable that its applied to. In this example, we assign a
                // padding of 16dp to the Card.
                Card(
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(16.dp)
                        .clickable(onClick = { selectShow(show.id) })
                ) {
                    // The Text composable is pre-defined by the Compose UI library; you can use this
                    // composable to render text on the screen
                    NetworkImageComponentPicasso(
                        url = show.poster_path,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    })
}