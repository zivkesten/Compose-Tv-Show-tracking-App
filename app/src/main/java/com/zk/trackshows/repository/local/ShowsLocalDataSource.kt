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
package com.zk.trackshows.repository.local

import androidx.paging.PagingSource
import com.zk.trackshows.repository.local.model.PopularShow
import com.zk.trackshows.repository.local.model.WatchedShow
import com.zk.trackshows.repository.LocalDataSource
import com.zk.trackshows.repository.local.dao.PopularShowsDao
import com.zk.trackshows.repository.local.dao.TopRatedShowsDao
import com.zk.trackshows.repository.local.dao.WatchListDao
import com.zk.trackshows.repository.local.model.TopRatedShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Concrete implementation of a data source as a db.
 */
@ExperimentalCoroutinesApi
class ShowsLocalDataSource internal constructor(
    private val watchListDao: WatchListDao,
    private val popularShowsDao: PopularShowsDao,
    private val topRatedShowsDao: TopRatedShowsDao
) : LocalDataSource {

    override suspend fun observeWatchedShows(): Flow<List<WatchedShow>> {
        val watchShows = watchListDao.getShows()
        return flow { emit(watchShows) }
    }

    override fun observePagedPopularShows(): PagingSource<Int, PopularShow> {
        return popularShowsDao.popularShowsPagingSource()
    }

    override suspend fun addToWatchList(show: WatchedShow) {
        watchListDao.insertShow(show)
    }

    override suspend fun removeFromWatchList(showId: Int) {
        watchListDao.deleteShow(showId)
    }

    override suspend fun clearPopularShowsCache() {
        popularShowsDao.deleteShows()
    }

    override suspend fun cachePopularShows(shows: List<PopularShow>): List<Long> {
        return popularShowsDao.insertAll(shows)
    }

    override suspend fun clearTopRatedShowsCache() {
        topRatedShowsDao.deleteShows()
    }

    override suspend fun cacheTopRatedShows(shows: List<TopRatedShow>) {
        topRatedShowsDao.insertAll(shows)
    }

}



