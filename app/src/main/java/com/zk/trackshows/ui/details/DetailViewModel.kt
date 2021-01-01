package com.zk.trackshows.ui.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zk.trackshows.data.repositories.WatchListRepository
import com.zk.trackshows.domain.model.Show
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val SHOW_KEY: String = "show"

data class DetailScreenState(
    val show: Show? = null,
    val isInWatchList: Boolean = false
)

sealed class Event {
    data class ScreenLoad(val data: Show): Event()
    data class TapAddToWatchList(val data: Show): Event()
}

@ExperimentalCoroutinesApi
class DetailViewModel @ViewModelInject constructor(
    private val watchListRepository: WatchListRepository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {


    private val _detailScreenState = MutableStateFlow(DetailScreenState())

    val detailScreenState: StateFlow<DetailScreenState> get() = _detailScreenState


    init {
        viewModelScope.launch {
            watchListRepository.observeWatchList().collect { watchList ->
                val show = state.get<Show>(SHOW_KEY)
                show?.let { currentShow ->
                    val watchListShow =
                        watchList.filter {
                                watchedShow -> watchedShow.id == currentShow.id
                        }
                    _detailScreenState.value = _detailScreenState.value.copy(
                        show = currentShow,
                        isInWatchList = watchListShow.isNotEmpty()
                    )
                }
            }
        }
    }
    fun onEvent(event: Event) {
        eventToResult(event)
    }

    private fun eventToResult(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.TapAddToWatchList -> {
                    watchListRepository.addToWatchList(event.data)
                }
                is Event.ScreenLoad -> {
                    val show = event.data
                    state.set(SHOW_KEY, show)
                    val watchList = watchListRepository.getWatchList()
                    val watchListShow = watchList.filter {
                            watchedShow -> watchedShow.id == show.id
                    }
                    _detailScreenState.value = _detailScreenState.value.copy(
                        show = show,
                        isInWatchList = watchListShow.isNotEmpty()
                    )
                }
            }
        }
    }
}
