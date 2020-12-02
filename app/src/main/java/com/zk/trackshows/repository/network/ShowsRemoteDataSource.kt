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

import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.Result
import com.zk.trackshows.repository.ShowsDataSource
import com.zk.trackshows.repository.network.api.TvShowsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the data source that adds a latency simulating network.
 */

@ExperimentalCoroutinesApi
class ShowsRemoteDataSource internal  constructor(
    private val service: TvShowsService,
    private val ioDispatcher: CoroutineDispatcher
): ShowsDataSource {

    override fun observeShows(): Flow<Result<List<Show>>> {
        TODO("Not yet implemented")
    }

    override fun observeShow(showId: String): Flow<Result<Show>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularShows(): Result<List<Show>?> {
        return when(val parsedResponse = responseMapper(service.fetchPopularShows())) {
            is NetworkResult.Success -> Result.Success(parsedResponse.data.shows)
            is NetworkResult.Error -> Result.Error(parsedResponse.exception)
        }
    }

    override suspend fun cacheShows(show: Show) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllShows() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshPopularShows() {
        TODO("Not yet implemented")
    }
}