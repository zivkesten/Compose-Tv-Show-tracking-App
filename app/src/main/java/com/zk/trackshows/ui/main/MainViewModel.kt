package com.zk.trackshows.ui.main

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.zk.trackshows.extensions.whenNotNull
import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.mockShows
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

data class ShowsState(
    val selectedShowId: Int? = 0,
    val show: Show? = null
)

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    private val appScreensNavigationChannel = ConflatedBroadcastChannel<AppScreens>()

    private val bottomNavigationScreensNavigationChannel = ConflatedBroadcastChannel<BottomNavigationScreens>()

    val shows = mockShows()

    val navigateTo: Flow<AppScreens> = appScreensNavigationChannel.asFlow()

    private val _state = MutableStateFlow(ShowsState())

    val state: MutableStateFlow<ShowsState> get() = _state

    fun tapShowEvent(showId: Int) {
        Log.d("Zivi", "tapShowEvent: $showId")

        val show = shows?.first() { showId == it.id }

        whenNotNull(show) {
            _state.value = _state.value.copy(show = it)
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