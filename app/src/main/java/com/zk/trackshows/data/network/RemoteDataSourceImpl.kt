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
package com.zk.trackshows.data.network

import com.zk.trackshows.data.RemoteDataSource
import com.zk.trackshows.data.network.api.TvShowResponse
import com.zk.trackshows.data.network.api.TvShowsService
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Implementation of the data source that adds a latency simulating network.
 */

@ExperimentalCoroutinesApi
class RemoteDataSourceImpl internal  constructor(
    private val service: TvShowsService
): RemoteDataSource {

    override suspend fun fetchPagedPopularShows(page: Int): TvShowResponse {
        return service.fetchPagedPopularShows(page)
    }

    override suspend fun fetchPagedTopRatedShows(page: Int): TvShowResponse {
        return service.fetchPagedTopRatedShows(page)
    }

    override suspend fun fetchPagedTrendingTVShows(page: Int): TvShowResponse {
        return service.fetchPagedTrendingTVShows(page)
    }
}