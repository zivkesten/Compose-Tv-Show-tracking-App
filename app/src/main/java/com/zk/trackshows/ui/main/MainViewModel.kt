package com.zk.trackshows.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.zk.trackshows.AppScreens
import com.zk.trackshows.common.InfoLogger.logMessage
import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.ShowsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


data class WatchListState(
    val loading: Boolean? = true,
    val error: Exception? = null,
    val watchedShows: List<Show>? = null,
    val pagingData: PagingData<Show>? = null
)

sealed class MainScreenEvent {
    object ScreenLoad: MainScreenEvent()
}

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    private val _navigationEvent = MutableStateFlow<AppScreens>(AppScreens.MainScreen)

    val navigationEvent: MutableStateFlow<AppScreens> get() = _navigationEvent

    private val _watchedShows = MutableStateFlow(WatchListState(loading = true))

    val watchedShows: StateFlow<WatchListState> get() = _watchedShows

    val popularShowsPagedData: Flow<PagingData<Show>> = showsRepository.observePagedPopularShows(true)


    fun tapShowEvent(show: Show) {
        logMessage("currentShowList()?.first { it.id == showId  } is ${show.name}")
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
            showsRepository.observeWatchList().collect {
                _watchedShows.value =
                    _watchedShows.value.copy(
                        loading = false,
                        error = null,
                        watchedShows = it)
            }
        }
    }
}
