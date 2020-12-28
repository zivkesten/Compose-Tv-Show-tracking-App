package com.zk.trackshows.data.repositories

import androidx.paging.PagingData
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.WatchedShow
import kotlinx.coroutines.flow.Flow

interface WatchListRepository {

    suspend fun addToWatchList(show: WatchedShow)

    suspend fun observeWatchList(): Flow<List<WatchedShow>>

    suspend fun removeFromWatchList(showId: Int)

}