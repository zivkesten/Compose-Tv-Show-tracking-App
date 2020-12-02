package com.zk.trackshows.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val data: Result<List<Show>>
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

    private suspend fun initPopularShowsFlow() {
        showsRepository.observePopularShows(true).collect {
            when (it) {
                is Result.Loading -> {
                    _popularShowsData.value = popularShows.value.copy(data = Result.Loading)
                }
                is Result.Error -> {
                    _popularShowsData.value =
                        popularShows.value.copy(data = Result.Error(it.exception))
                }
                is Result.Success -> {
                    _popularShowsData.value =
                        popularShows.value.copy(data = Result.Success(it.data))
                }
            }
        }
    }

    fun tapShowEvent(show: Show) {
        logMessage("currentShowList()?.first { it.id == showId  } is ${show.name}")
            _navigationEvent.value = AppScreens.Details(show)
    }

    fun tapSearch() {
        _navigationEvent.value = AppScreens.Search
    }
}