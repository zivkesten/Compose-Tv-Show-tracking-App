package com.zk.trackshows.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
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
                TrackShowsMain(
                    viewModel = viewModel,
                    backDispatcher = onBackPressedDispatcher
                )
            }
        }
    }
}
