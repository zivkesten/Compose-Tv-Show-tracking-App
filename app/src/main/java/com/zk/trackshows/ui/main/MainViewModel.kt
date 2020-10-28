package com.zk.trackshows.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zk.trackshows.model.Show
import com.zk.trackshows.ui.mainScreens.popularShowsGenerator
import com.zk.trackshows.ui.mainScreens.showJson
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {

    val shows = popularShowsGenerator(showJson())?.shows

    private var _showDetails: LiveData<Show> = MutableLiveData()
    val showDetails: LiveData<Show> get() = _showDetails

    fun getShow(showId: Int) {

        val details = shows?.first { showId == it.id } ?: return
        _showDetails = flow { emit(details) }.asLiveData()
    }
}