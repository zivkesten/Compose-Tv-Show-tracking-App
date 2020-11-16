package com.zk.trackshows.ui.mainScreens

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.material.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
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
import com.zk.trackshows.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun DiscoverScreen(viewModel: MainViewModel) {

    ScrollableColumn {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            SearchBox(tapSearch = { viewModel.tapSearch() })
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalList("Trending now", Icons.Filled.Fireplace, viewModel)
            HorizontalList("Returning this wee", Icons.Filled.Satellite, viewModel)
            HorizontalList(icon = Icons.Outlined.Cake, viewModel = viewModel)
            HorizontalList(viewModel = viewModel)
            HorizontalList(icon = Icons.Outlined.Gamepad, viewModel = viewModel)
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
