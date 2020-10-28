package com.zk.trackshows.ui.navigation.detinations

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.zk.trackshows.ui.navigation.Navigator
import kotlinx.android.parcel.Parcelize

sealed class Destinations : Parcelable {

    @Parcelize
    object Home : Destinations()

    @Immutable
    @Parcelize
    data class ShowDetail(val showId: Int) : Destinations()

    @Immutable
    @Parcelize
    object SearchScreen : Destinations()
}

class Actions(navigator: Navigator<Destinations>) {

    val selectShow: (Int) -> Unit = { showId: Int ->
        navigator.navigate(Destinations.ShowDetail(showId))
    }

    val tapSearch: () -> Unit = {  ->
        navigator.navigate(Destinations.SearchScreen)
    }

    val pressOnBack: () -> Unit = {
        navigator.back()
    }
}
