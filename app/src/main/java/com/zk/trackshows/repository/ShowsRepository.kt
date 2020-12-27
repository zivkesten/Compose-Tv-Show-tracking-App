package com.zk.trackshows.repository

import androidx.paging.PagingData
import com.zk.trackshows.repository.local.model.PopularShow
import com.zk.trackshows.repository.local.model.WatchedShow
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {

    fun popularShowsPagingData(forceUpdate: Boolean): Flow<PagingData<PopularShow>>

    suspend fun addToWatchList(show: WatchedShow)

    suspend fun observeWatchList(): Flow<List<WatchedShow>>

    suspend fun removeFromWatchList(showId: Int)

}