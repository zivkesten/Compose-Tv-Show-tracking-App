package com.zk.trackshows.data

import androidx.paging.PagingSource
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.TopRatedShow
import com.zk.trackshows.data.local.model.TrendingShow
import com.zk.trackshows.data.local.model.WatchedShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
interface DiscoverShowsLocalDataSource {

    fun observePagedPopularShows(): PagingSource<Int, PopularShow>

    fun observePagedTopRatedShows(): PagingSource<Int, TopRatedShow>

    fun observePagedTrendingShows(): PagingSource<Int, TrendingShow>

    suspend fun clearPopularShowsCache()

    suspend fun clearTrendingShowsCache()

    suspend fun clearTopRatedShowsCache()

    suspend fun cachePopularShows(shows: List<PopularShow>)

    suspend fun cacheTopRatedShows(shows: List<TopRatedShow>)

    suspend fun cacheTrendingShows(shows: List<TrendingShow>)

}