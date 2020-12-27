package com.zk.trackshows.ui.details

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.repository.ShowsRepository
import com.zk.trackshows.repository.local.model.ShowEntityMapper
import com.zk.trackshows.repository.local.model.WatchedShow
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
    private val showsRepository: ShowsRepository,
    private val entityMapper: ShowEntityMapper
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
                    showsRepository.addToWatchList(WatchedShow(entityMapper.mapFromDomainModel(event.data)))
                }
                is Event.ScreenLoad -> {
                    val show = event.data
                    showsRepository.observeWatchList().collect {
                        Log.i("Zivi", "observeWatchList detail: $it")
                        val watchListShow = it.filter { watchedShow -> watchedShow.show.id == show.id }
                        _detailScreenState.value = _detailScreenState.value.copy(
                            show = show,
                            isInWatchList = watchListShow.isNotEmpty()

                        )
                    }
                }
            }
        }
    }
}
