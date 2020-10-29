package com.zk.trackshows.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import com.zk.trackshows.ui.main.ui.TrackShowsTheme

class MainActivity : AppCompatActivity() {

    @VisibleForTesting
    val viewModel = MainViewModel()

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
               MainScreen(viewModel = viewModel, selectShow = {}, tapSearch = {})
            }
        }

    }
}
