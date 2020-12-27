package com.zk.trackshows.repository

import androidx.paging.PagingSource
import com.zk.trackshows.repository.local.model.PopularShow
import com.zk.trackshows.repository.local.model.TopRatedShow
import com.zk.trackshows.repository.local.model.WatchedShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
interface LocalDataSource {

    fun observePagedPopularShows(): PagingSource<Int, PopularShow>

    suspend fun observeWatchedShows(): Flow<List<WatchedShow>>

    suspend fun addToWatchList(show: WatchedShow)

    suspend fun removeFromWatchList(showId: Int)

    suspend fun clearPopularShowsCache()

    suspend fun clearTopRatedShowsCache()

    suspend fun cachePopularShows(shows: List<PopularShow>)

    suspend fun cacheTopRatedShows(shows: List<TopRatedShow>)

}