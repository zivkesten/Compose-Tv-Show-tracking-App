package com.zk.trackshows

import android.app.Application
import com.zk.trackshows.domain.model.Show
import dagger.hilt.android.HiltAndroidApp

sealed class AppScreens {
    data class Details(val show: Show): AppScreens()
    object Search: AppScreens()
    object MainScreen: AppScreens()
}


@HiltAndroidApp
class TrackShowsApp : Application()