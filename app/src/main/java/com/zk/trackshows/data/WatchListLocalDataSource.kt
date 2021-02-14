package com.zk.trackshows.data

import com.zk.trackshows.data.local.model.WatchedShow
import com.zk.trackshows.domain.model.Show
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
interface WatchListLocalDataSource {

    suspend fun observeWatchedShows(): Flow<List<Show>>

    suspend fun getWatchList(): List<Show>

    suspend fun addToWatchList(show: Show)

    suspend fun removeFromWatchList(showId: Int)

}