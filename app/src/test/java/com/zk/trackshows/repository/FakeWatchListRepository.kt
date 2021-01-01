package com.zk.trackshows.repository

import com.zk.trackshows.data.repositories.WatchListRepository
import com.zk.trackshows.domain.model.Show
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
class FakeWatchListRepository: WatchListRepository {

    var watchedShowsStateFlow = MutableStateFlow<List<Show>>(listOf())

    override suspend fun addToWatchList(show: Show) {
        val list: MutableList<Show> = watchedShowsStateFlow.value.toMutableList()
        list.add(show)
        watchedShowsStateFlow.value = list
    }

    override suspend fun observeWatchList(): Flow<List<Show>> {
        return watchedShowsStateFlow
    }

    override fun getWatchList(): List<Show> {
        return watchedShowsStateFlow.value
    }

    override suspend fun removeFromWatchList(showId: Int) {
        val list: MutableList<Show> = watchedShowsStateFlow.value.toMutableList()
        list.removeIf { it.id == showId }
        watchedShowsStateFlow.value = list
    }
}