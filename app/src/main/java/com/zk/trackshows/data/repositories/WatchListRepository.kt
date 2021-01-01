package com.zk.trackshows.data.repositories

import androidx.paging.PagingData
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.WatchedShow
import com.zk.trackshows.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface WatchListRepository {

    suspend fun addToWatchList(show: Show)

    suspend fun observeWatchList(): Flow<List<Show>>

    fun getWatchList(): List<Show>

    suspend fun removeFromWatchList(showId: Int)

}