package com.zk.trackshows.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.zk.trackshows.AppScreens
import com.zk.trackshows.common.InfoLogger.logMessage
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.mapper.ShowEntityMapper
import com.zk.trackshows.data.repositories.DiscoverShowsRepository
import com.zk.trackshows.data.repositories.WatchListRepository
import com.zk.trackshows.domain.model.Show
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


data class WatchListState(
    val loading: Boolean? = true,
    val error: Exception? = null,
    val watchedShows: List<Show>? = null,
    val pagingData: PagingData<PopularShow>? = null
)

sealed class MainScreenEvent {
    object ScreenLoad: MainScreenEvent()
}

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val watchListRepository: WatchListRepository,
    discoverShowsRepository: DiscoverShowsRepository,
) : ViewModel() {

    private val _navigationEvent = MutableStateFlow<AppScreens>(AppScreens.MainScreen)

    val navigationEvent: MutableStateFlow<AppScreens> get() = _navigationEvent

    private val _watchListState = MutableStateFlow(WatchListState(loading = true))

    val watchListState: StateFlow<WatchListState> get() = _watchListState

    val popularShowsPagedData: Flow<PagingData<Show>> = discoverShowsRepository.popularShowsPagingData()

    val topRatedShowsPagedData: Flow<PagingData<Show>> = discoverShowsRepository.topRatedShowsPagingData()

    val trendingShowsPagedData: Flow<PagingData<Show>> = discoverShowsRepository.trendingShowsPagingData()


    fun tapShowEvent(show: Show) {
        _navigationEvent.value = AppScreens.Details(show)
    }

    fun tapSearch() {
        _navigationEvent.value = AppScreens.Search
    }

    fun onEvent(event: MainScreenEvent) {
        when(event) {
            is MainScreenEvent.ScreenLoad -> {
                screenLoadToResult()
            }
        }
    }

    private fun screenLoadToResult() {
        viewModelScope.launch {
            watchListRepository.observeWatchList().collect { watchedShows ->
                _watchListState.value =
                    _watchListState.value.copy(
                        loading = false,
                        error = null,
                        watchedShows = watchedShows
                    )
            }
        }
    }
}
