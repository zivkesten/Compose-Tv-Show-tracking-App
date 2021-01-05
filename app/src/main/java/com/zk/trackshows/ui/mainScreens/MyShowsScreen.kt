package com.zk.trackshows.ui.mainScreens

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zk.trackshows.ui.components.LazyPagingRowWithPagingData
import com.zk.trackshows.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun MyShowsScreen(viewModel: MainViewModel) {

    val statusBarHeight = 32.dp
    val showLoading = remember { mutableStateOf(true) }
    val listOfSections = listOf(
        "Trending this week",
        "Popular this week",
        "Top rated movies",
        "Trending TV shows",
        "Top rated TV shows",
    )
    ScrollableColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(statusBarHeight))

        if (showLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        listOfSections.forEach { title ->
            LazyPagingRowWithPagingData(
                title = title,
                viewModel::tapShowEvent,
                viewModel.popularShowsPagedData
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

//@ExperimentalCoroutinesApi
//@FlowPreview
//@Composable
//fun DynamicSection(
//    type: String,
//    viewModel: MainViewModel,
//    showLoading: MutableState<Boolean>,
//) {
//    val shows by viewModel.popularShows.collectAsState()
//
//    when (val showList = shows.data) {
//        is Result.Loading -> { showLoading.value = true }
//        is Result.Error -> { showLoading.value = false }
//        is Result.Success -> {
//            showLoading.value = false
//            if (showList.data.isNotEmpty()) {
//                MoviesLaneItem(shows = showList.data, title = type, viewModel = viewModel)
//            }
//        }
//    }
//}