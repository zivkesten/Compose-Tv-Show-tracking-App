package com.zk.trackshows.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.ui.theme.TrackShowsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    val show by lazy {
        intent.getParcelableExtra(SHOW) as Show?
    }

    val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TrackShowsTheme {
                ShowDetails(viewModel, show)
            }
        }
    }

    companion object {
        const val SHOW = "show"
        fun newIntent(context: Context, show: Show) =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(SHOW, show)
            }
    }
}