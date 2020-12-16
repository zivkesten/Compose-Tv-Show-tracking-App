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
import com.zk.trackshows.model.Show
import com.zk.trackshows.model.WatchedShow
import com.zk.trackshows.repository.Result
import com.zk.trackshows.repository.ShowsDataSource
import com.zk.trackshows.repository.network.api.TvShowResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Concrete implementation of a data source as a db.
 */
@ExperimentalCoroutinesApi
class ShowsLocalDataSource internal constructor(
    private val showsDao: ShowsDao,
    private val watchListDao: WatchListDao,
    private val remoteKeys: RemoteKeysDao
) : ShowsDataSource {

    override suspend fun observeWatchList(): Flow<List<Show>> {
        val watchedShowsIds = watchListDao.getShows().map { it.showId }
        return showsDao.observeSelectedShowsShows(watchedShowsIds)
    }

    override fun observeWatchedShow(showId: Int): Flow<WatchedShow> {
        return watchListDao.observeShow(showId)
    }

    override fun observePagedShows(): PagingSource<Int, Show> {
        return showsDao.showsPagingSource()
    }

    override fun observeShows(): Flow<Result<List<Show>>> {
        return showsDao.observeShows().map {
            Result.Success(it)
        }
    }

    override fun observeShow(showId: String): Flow<Result<Show>> {
        return showsDao.observeShowById(showId).map {
            Result.Success(it)
        }
    }

    override suspend fun getPopularShows(): List<Show> {
        return showsDao.getShows()
    }

    override suspend fun getPagedPopularShows(page: Int): TvShowResponse {
       return TvShowResponse(100000, getPopularShows())
    }

    override suspend fun cacheShow(show: Show) {
        showsDao.insertShow(show)
    }

    override suspend fun getKeys(): List<RemoteKeys>? {
        return remoteKeys.getKeys()
    }

    override suspend fun cacheShows(shows: List<Show>) {
        showsDao.insertAll(shows)
    }

    override suspend fun deleteAllShows() {
        showsDao.deleteShows()
    }

    override suspend fun refreshPopularShows() {
        // NO-OP
    }

    override suspend fun addToWatchList(show: WatchedShow) {
        watchListDao.insertShow(show)
    }

    override suspend fun removeFromWatchList(showId: Int) {
        watchListDao.deleteShow(showId)
    }

    suspend fun insertAll(remoteKey: List<RemoteKeys>) {
        remoteKeys.insertAll(remoteKey)
    }

    suspend fun remoteKeysShowId(showId: Int): RemoteKeys? {
        return remoteKeys.remoteKeysShowId(showId)
    }

    suspend fun clearRemoteKeys() {
        remoteKeys.clearRemoteKeys()
    }
}



