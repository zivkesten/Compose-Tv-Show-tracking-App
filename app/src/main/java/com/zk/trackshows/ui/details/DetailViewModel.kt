package com.zk.trackshows.ui.details

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zk.trackshows.model.Show
import com.zk.trackshows.model.WatchedShow
import com.zk.trackshows.repository.ShowsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
    private val showsRepository: ShowsRepository
) : ViewModel() {

    private val _detailScreenState = MutableStateFlow(DetailScreenState())

    val detailScreenState: StateFlow<DetailScreenState> get() = _detailScreenState

    fun onEvent(event: Event) {
        eventToResult(event)
    }

    private fun eventToResult(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is Event.TapAddToWatchList -> {
                    showsRepository.addToWatchList(WatchedShow(showId = event.data.id))

                }
                is Event.ScreenLoad -> {
                    val show = event.data
                    showsRepository.observeWatchList().collect {
                        _detailScreenState.value = _detailScreenState.value.copy(
                            show = show,
                            isInWatchList = it.contains(show)
                        )
                    }
                }
            }
        }
    }
}
