package com.zk.trackshows.data.local

import androidx.paging.PagingSource
import com.zk.trackshows.data.DiscoverShowsLocalDataSource
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.TopRatedShow
import com.zk.trackshows.data.local.model.TrendingShow
import com.zk.trackshows.utils.MockPopularShowsPagingSource
import com.zk.trackshows.utils.mockPopularShows

class FakeDiscoverShowsLocalDataSource: DiscoverShowsLocalDataSource {

    val popularShowData = mutableListOf<PopularShow>()
    val topRatedShowData = mutableListOf<TopRatedShow>()
    val trendingShowData = mutableListOf<TrendingShow>()

    override fun observePagedPopularShows(): PagingSource<Int, PopularShow> {
        return MockPopularShowsPagingSource(mockPopularShows)
    }

    override fun observePagedTopRatedShows(): PagingSource<Int, TopRatedShow> {
        TODO("Not yet implemented")
    }

    override fun observePagedTrendingShows(): PagingSource<Int, TrendingShow> {
        TODO("Not yet implemented")
    }

    override suspend fun clearPopularShowsCache() {
        TODO("Not yet implemented")
    }

    override suspend fun clearTrendingShowsCache() {
        TODO("Not yet implemented")
    }

    override suspend fun clearTopRatedShowsCache() {
        TODO("Not yet implemented")
    }

    override suspend fun cachePopularShows(shows: List<PopularShow>) {
        TODO("Not yet implemented")
    }

    override suspend fun cacheTopRatedShows(shows: List<TopRatedShow>) {
        TODO("Not yet implemented")
    }

    override suspend fun cacheTrendingShows(shows: List<TrendingShow>) {
        TODO("Not yet implemented")
    }
}