package com.zk.trackshows.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.setContent
import com.zk.trackshows.ui.theme.TrackShowsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    @VisibleForTesting
    private val viewModel: MainViewModel by viewModels()

    @FlowPreview
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        // observe toast.
//        viewModel.toast.observe(this) {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//        }

        // fetch shows
//        viewModel.fetchShowList()

        // set disney contents.
        setContent {
            TrackShowsTheme() {
               TrackShowsApp(viewModel = viewModel)
            }
        }

    }
}
