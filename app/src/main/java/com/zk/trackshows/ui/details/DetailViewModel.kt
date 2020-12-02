package com.zk.trackshows.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.ShowsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DetailScreenState(
    val show: Show? = null
)

sealed class Event() {
    data class ScreenLoad(val data: Show): Event()
}

@ExperimentalCoroutinesApi
class DetailViewModel @ViewModelInject constructor(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    private val _detailScreenState = MutableStateFlow(DetailScreenState())

    val detailScreenState: StateFlow<DetailScreenState> get() = _detailScreenState

    fun onEvent(event: Event) {
        when (event) {
            is Event.ScreenLoad -> {
                 _detailScreenState.value = _detailScreenState.value.copy(show = event.data)
            }
        }
    }

}
