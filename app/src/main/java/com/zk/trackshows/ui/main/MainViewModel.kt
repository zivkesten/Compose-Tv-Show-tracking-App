package com.zk.trackshows.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.zk.trackshows.AppScreens
import com.zk.trackshows.common.InfoLogger.logMessage
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.ShowEntityMapper
import com.zk.trackshows.data.repositories.DiscoverShowsRepository
import com.zk.trackshows.data.repositories.WatchListRepository
import com.zk.trackshows.domain.model.Show
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
    private val discoverShowsRepository: DiscoverShowsRepository,
    private val entityMapper: ShowEntityMapper
) : ViewModel() {

    private val _navigationEvent = MutableStateFlow<AppScreens>(AppScreens.MainScreen)

    val navigationEvent: MutableStateFlow<AppScreens> get() = _navigationEvent

    private val _watchedShows = MutableStateFlow(WatchListState(loading = true))

    val watchedShows: StateFlow<WatchListState> get() = _watchedShows

    // This flow originates from a PopularShow stream and mapped to Show
    val popularShowsPagedData: Flow<PagingData<Show>> =
        discoverShowsRepository.popularShowsPagingData(true)
            .map { pagingData -> pagingData.map { popularShow ->
                        entityMapper.mapToDomainModel(popularShow.show)
                    }
            }

    // This flow originates from a TopReted stream and mapped to Show
    val topRatedShowsPagedData: Flow<PagingData<Show>> =
        discoverShowsRepository.popularShowsPagingData(true)
            .map { pagingData -> pagingData.map { popularShow ->
                entityMapper.mapToDomainModel(popularShow.show)
            }
            }

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
            Log.i("Zivi", "screenLoadToResult")
            watchListRepository.observeWatchList().collect { watchedShows ->
                Log.v("Zivi", "observeWatchList: ${watchedShows.size}")
                val entities = watchedShows.map { it.show }
                val shows = entityMapper.toDomainList(entities)
                _watchedShows.value =
                    _watchedShows.value.copy(
                        loading = false,
                        error = null,
                        watchedShows = shows
                    )
            }
        }
    }
}
