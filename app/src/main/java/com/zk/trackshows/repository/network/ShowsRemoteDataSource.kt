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
package com.zk.trackshows.repository.network

import androidx.paging.PagingSource
import com.zk.trackshows.model.Show
import com.zk.trackshows.model.WatchedShow
import com.zk.trackshows.repository.Result
import com.zk.trackshows.repository.ShowsDataSource
import com.zk.trackshows.repository.local.RemoteKeys
import com.zk.trackshows.repository.network.api.RemotePagingSource
import com.zk.trackshows.repository.network.api.TvShowResponse
import com.zk.trackshows.repository.network.api.TvShowsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the data source that adds a latency simulating network.
 */

@ExperimentalCoroutinesApi
class ShowsRemoteDataSource internal  constructor(
    private val service: TvShowsService
): ShowsDataSource {
    override suspend fun observeWatchList(): Flow<List<Show>> {
        TODO("Not yet implemented")
    }

    override fun observeWatchedShow(showId: Int): Flow<WatchedShow> {
        TODO("Not yet implemented")
    }

    override fun observePagedShows(): PagingSource<Int, Show> {
        return RemotePagingSource(service::fetchPagedPopularShows)
    }

    override fun observeShows(): Flow<Result<List<Show>>> {
        TODO("Not yet implemented")
    }

    override fun observeShow(showId: String): Flow<Result<Show>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularShows(): List<Show> {
        return when(val parsedResponse = responseMapper(service.fetchPopularShows())) {
            is NetworkResult.Success -> parsedResponse.data.shows
            is NetworkResult.Error -> emptyList()
        }
    }

    override suspend fun getPagedPopularShows(page: Int): TvShowResponse {
        return service.fetchPagedPopularShows(page)
    }

    override suspend fun cacheShow(show: Show) {
        TODO("Not yet implemented")
    }

    override suspend fun getKeys(): List<RemoteKeys>? {
        TODO("Not yet implemented")
    }

    override suspend fun cacheShows(shows: List<Show>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllShows() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshPopularShows() {
        TODO("Not yet implemented")
    }

    override suspend fun addToWatchList(show: WatchedShow) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromWatchList(showId: Int) {
        TODO("Not yet implemented")
    }
}