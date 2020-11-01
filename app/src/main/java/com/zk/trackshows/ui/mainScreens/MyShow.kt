package com.zk.trackshows.ui.mainScreens

import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.BabyChangingStation
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zk.trackshows.components.HorizontalScrollableComponent
import com.zk.trackshows.extensions.whenNotNull
import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.mockShows

@Composable
fun MyShows(navController: NavController, selectShow: (Int) -> Unit) {
    val shows = mockShows()
    ScrollableColumn {
        Column(modifier = Modifier
                .padding(16.dp)
        ) {
            HorizontalList("Title 1", Icons.Outlined.AddBox, shows, selectShow)
            HorizontalList("Title 2", Icons.Outlined.BabyChangingStation, shows, selectShow)
            HorizontalList("Title 2", Icons.Outlined.Cake, shows, selectShow)
        }
    }
}

@Composable
fun HorizontalList(title: String? = null, icon: VectorAsset? = null, shows: List<Show>?, selectShow: (Int) -> Unit) {
    HorizontalListTitle(title, icon)
    Shows(shows, selectShow)
}

@Composable
private fun HorizontalListTitle(text: String? = null, icon: VectorAsset? = null) {
    Row() {
        whenNotNull(icon) { Icon(asset = it) }
        whenNotNull(text) { Text(it) }
    }
}

@Composable
private fun Shows(shows: List<Show>?, selectShow: (Int) -> Unit) {
    if (shows != null) {
        HorizontalScrollableComponent(showList = shows, selectShow)
    } else {
        Text("No Shows")
    }
}