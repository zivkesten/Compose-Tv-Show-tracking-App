package com.zk.trackshows.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zk.trackshows.extensions.whenNotNull
import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.Result
import com.zk.trackshows.repository.ShowsRepository
import com.zk.trackshows.repository.succeeded
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

//sealed class Action() {
//    object ScreenLoad: Action()
//}

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

    val navigateTo: Flow<AppScreens> = appScreensNavigationChannel.asFlow()

    private val _showState = MutableStateFlow(ShowsState())

    val showState: MutableStateFlow<ShowsState> get() = _showState

    private val _watchListState = MutableStateFlow(WatchListState(Result.Loading))

    val watchListState: StateFlow<WatchListState> get() = _watchListState

    init {
        viewModelScope.launch {
            showsRepository.refreshPopularShows()
            showsRepository.observePopularShows().collect {
                when (it) {
                    is Result.Loading -> {
                        _watchListState.value = watchListState.value.copy(data = Result.Loading)
                    }
                    is Result.Error -> {
                        _watchListState.value = watchListState.value.copy(data = Result.Error(it.exception))
                    }
                    is Result.Success -> {
                        _watchListState.value = watchListState.value.copy(data = Result.Success(it.data))
                    }
                }
            }
        }
    }

    fun tapShowEvent(showId: Int) {
        val show = currentShowList()?.first { it.id == showId  }
        whenNotNull(show) {
            _showState.value = _showState.value.copy(show = it)
            navigateToAppScreen(AppScreens.Details)
        }
    }

    fun tapSearch() {
        navigateToAppScreen(AppScreens.Search)
    }

    private fun navigateToAppScreen(screen: AppScreens) {
        viewModelScope.launch {
            appScreensNavigationChannel.send(screen)
        }
    }

    fun currentShowList(): List<Show>? {
        return if (watchListState.value.data.succeeded) {
            (watchListState.value.data as Result.Success<List<Show>>).data
        } else { null }
    }
}