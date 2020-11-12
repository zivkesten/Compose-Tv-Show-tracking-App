package com.zk.trackshows.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zk.trackshows.extensions.whenNotNull
import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.Result
import com.zk.trackshows.repository.ShowsRepository
import com.zk.trackshows.repository.mockShows
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

sealed class Action() {
    object ScreenLoad: Action()
}

data class ShowsState(
    val selectedShowId: Int? = 0,
    val show: Show? = null
)

data class WatchListState(
    val data: Result<List<Show>>
)

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    private val appScreensNavigationChannel = ConflatedBroadcastChannel<AppScreens>()

    private val bottomNavigationScreensNavigationChannel = ConflatedBroadcastChannel<BottomNavigationScreens>()

    val shows = mockShows()

    val navigateTo: Flow<AppScreens> = appScreensNavigationChannel.asFlow()

    private val _showState = MutableStateFlow(ShowsState())

    val showState: MutableStateFlow<ShowsState> get() = _showState

    private val _watchListState = MutableStateFlow(WatchListState(Result.Loading))

    val watchListState: MutableStateFlow<WatchListState> get() = _watchListState

    init {

        viewModelScope.launch {

            mockShows()?.forEach {
                showsRepository.cacheShow(it)
            }

            showsRepository.observeShows().collect {
                when (it) {
                    is Result.Loading -> _watchListState.value =
                        watchListState.value.copy(data = Result.Loading)
                    is Result.Error -> _watchListState.value =
                        watchListState.value.copy(data = Result.Error(it.exception))
                    is Result.Success -> _watchListState.value =
                        watchListState.value.copy(data = Result.Success(it.data))
                }
            }
        }
    }

    fun tapShowEvent(showId: Int) {
        val show = shows?.first() { showId == it.id }

        whenNotNull(show) {
            _showState.value = _showState.value.copy(show = it)
            navigateToAppScreen(AppScreens.Details)
        }
    }

    private fun navigateToAppScreen(screen: AppScreens): Job {
        return viewModelScope.launch {
            appScreensNavigationChannel.send(screen)
        }
    }

    private fun navigateToBottomNaviogationScreen(screen: BottomNavigationScreens): Job {
        return viewModelScope.launch {
            bottomNavigationScreensNavigationChannel.send(screen)
        }
    }
}