package com.zk.trackshows.data

import androidx.paging.PagingSource
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.TopRatedShow
import com.zk.trackshows.data.local.model.WatchedShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
interface LocalDataSource {

    fun observePagedPopularShows(): PagingSource<Int, PopularShow>

    fun observePagedTopRatedShows(): PagingSource<Int, TopRatedShow>

    suspend fun observeWatchedShows(): Flow<List<WatchedShow>>

    suspend fun addToWatchList(show: WatchedShow)

    suspend fun removeFromWatchList(showId: Int)

    suspend fun clearPopularShowsCache()

    suspend fun clearTopRatedShowsCache()

    suspend fun cachePopularShows(shows: List<PopularShow>): List<Long>

    suspend fun cacheTopRatedShows(shows: List<TopRatedShow>)

}