package com.zk.trackshows.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.zk.trackshows.AppScreens
import com.zk.trackshows.common.InfoLogger.logMessage
import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.Result
import com.zk.trackshows.repository.ShowsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


data class WatchListState(
    val data: Result<List<Show>>,
    val pagedData: PagingSource<Int, Show>? = null
)

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    private val _navigationEvent = MutableStateFlow<AppScreens>(AppScreens.MainScreen)

    val navigationEvent: MutableStateFlow<AppScreens> get() = _navigationEvent

    private val _popularShowsData = MutableStateFlow(WatchListState(Result.Loading))

    val popularShows: StateFlow<WatchListState> get() = _popularShowsData



    init {
        viewModelScope.launch {
            initPopularShowsFlow()
        }
    }

    suspend fun observePopularShows(): PagingSource<Int, Show> {
        return showsRepository.observePopularShows(true)
    }

    private suspend fun initPopularShowsFlow() {
        val pager = showsRepository.observePopularShows(true)
        _popularShowsData.value = _popularShowsData.value.copy(pagedData = pager)
    }

    fun tapShowEvent(show: Show) {
        logMessage("currentShowList()?.first { it.id == showId  } is ${show.name}")
        _navigationEvent.value = AppScreens.Details(show)
    }

    fun tapSearch() {
        _navigationEvent.value = AppScreens.Search
    }
}