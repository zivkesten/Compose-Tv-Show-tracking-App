package com.zk.trackshows.ui.mainScreens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Gamepad
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DiscoverScreen(navController: NavController, selectShow: (Int) -> Unit, tapSearch: () -> Unit) {

    val shows = popularShowsGenerator(showJson())?.shows
    ScrollableColumn {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            SearchBox(tapSearch = tapSearch)
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalList("Trending now", Icons.Filled.Fireplace, shows, selectShow)
            HorizontalList("Returning this wee", Icons.Filled.Satellite, shows, selectShow)
            HorizontalList(icon = Icons.Outlined.Cake, shows = shows, selectShow = selectShow)
            HorizontalList(shows = shows, selectShow = selectShow)
            HorizontalList(icon = Icons.Outlined.Gamepad, shows = shows, selectShow = selectShow)
        }
    }
}

@Composable
fun SearchBox(tapSearch: () -> Unit) {
    Box(
        modifier = Modifier
            .background(Color.DarkGray, shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .height(40.dp)
            .padding(4.dp)
            .clickable(onClick = tapSearch)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(asset = Icons.Outlined.Search, modifier = Modifier.padding(4.dp))
            Text(text = "Tap to search", style = TextStyle(Color.Gray, 10.sp))
        }
    }
}
