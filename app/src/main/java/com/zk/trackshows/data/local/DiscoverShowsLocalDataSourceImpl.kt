/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zk.trackshows.data.local

import androidx.paging.PagingSource
import com.zk.trackshows.data.DiscoverShowsLocalDataSource
import com.zk.trackshows.data.local.dao.PopularShowsDao
import com.zk.trackshows.data.local.dao.TopRatedShowsDao
import com.zk.trackshows.data.local.dao.TrendingShowsDao
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.TopRatedShow
import com.zk.trackshows.data.local.model.TrendingShow
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Concrete implementation of a data source as a db.
 */
@ExperimentalCoroutinesApi
class DiscoverShowsLocalDataSourceImpl internal constructor(
    private val popularShowsDao: PopularShowsDao,
    private val topRatedShowsDao: TopRatedShowsDao,
    private val trendingShowsDao: TrendingShowsDao

    ) : DiscoverShowsLocalDataSource {

    override fun observePagedPopularShows(): PagingSource<Int, PopularShow> {
        return popularShowsDao.popularShowsPagingSource()
    }

    override fun observePagedTopRatedShows(): PagingSource<Int, TopRatedShow> {
        return topRatedShowsDao.topRatedPagingSource()
    }

    override fun observePagedTrendingShows(): PagingSource<Int, TrendingShow> {
        return trendingShowsDao.trendingPagingSource()
    }

    override suspend fun clearPopularShowsCache() {
        popularShowsDao.deleteShows()
    }

    override suspend fun clearTrendingShowsCache() {
        trendingShowsDao.deleteShows()
    }

    override suspend fun cachePopularShows(shows: List<PopularShow>) {
        return popularShowsDao.insertAll(shows)
    }

    override suspend fun clearTopRatedShowsCache() {
        topRatedShowsDao.deleteShows()
    }

    override suspend fun cacheTopRatedShows(shows: List<TopRatedShow>) {
        topRatedShowsDao.insertAll(shows)
    }

    override suspend fun cacheTrendingShows(shows: List<TrendingShow>) {
        trendingShowsDao.insertAll(shows)
    }
}



