package com.zk.trackshows.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.zk.trackshows.model.Show
import com.zk.trackshows.model.WatchedShow
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {

    suspend fun refreshPopularShows()

    fun observePagedPopularShows(forceUpdate: Boolean): Flow<PagingData<Show>>

    suspend fun getPopularShows(forceUpdate: Boolean): Result<List<Show>?>

    suspend fun cacheShow(show: Show)

    suspend fun deleteAllShows()

    suspend fun addToWatchList(show: WatchedShow)

    suspend fun observeWatchedShow(showId: Int): Flow<WatchedShow>

    suspend fun observeWatchList(): Flow<List<Show>>

    suspend fun removeFromWatchList(showId: Int)

}