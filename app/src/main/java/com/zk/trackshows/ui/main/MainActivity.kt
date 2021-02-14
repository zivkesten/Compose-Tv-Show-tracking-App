package com.zk.trackshows.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.lifecycleScope
import com.zk.trackshows.AppScreens
import com.zk.trackshows.common.InfoLogger.logMessage
import com.zk.trackshows.data.network.api.TvShowsService
import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.ui.details.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

sealed class MainScreenInteractionEvents {
    data class NavigateTo(val route: String) : MainScreenInteractionEvents()
    data class AddToMyWatchlist(val show: Show) : MainScreenInteractionEvents()
    data class RemoveFromMyWatchlist(val show: Show) : MainScreenInteractionEvents()
}

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    @VisibleForTesting
    private val viewModel: MainViewModel by viewModels()

    @InternalCoroutinesApi
    @FlowPreview
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainScreen(viewModel = viewModel)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collect { screen ->
                when (screen) {
                    is AppScreens.Details -> {
                        startActivity(
                            DetailActivity.newIntent(this@MainActivity, screen.show)
                        )
                    }
                    is AppScreens.Search -> logMessage("navigationEvent search screen")
                    is AppScreens.MainScreen -> logMessage("No navigationEvent")
                }
            }
        }
    }
}
