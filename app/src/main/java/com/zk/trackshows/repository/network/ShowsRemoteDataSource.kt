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
import com.zk.trackshows.repository.RemoteDataSource
import com.zk.trackshows.repository.network.api.RemotePagingSource
import com.zk.trackshows.repository.network.api.TvShowResponse
import com.zk.trackshows.repository.network.api.TvShowsService
import com.zk.trackshows.repository.network.model.ShowDto
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Implementation of the data source that adds a latency simulating network.
 */

@ExperimentalCoroutinesApi
class ShowsRemoteDataSource internal  constructor(
    private val service: TvShowsService
): RemoteDataSource {

    override fun observePagedShows(): PagingSource<Int, ShowDto> {
        return RemotePagingSource(service::fetchPagedPopularShows)
    }

    override suspend fun fetchPagedPopularShows(page: Int): TvShowResponse {
        return service.fetchPagedPopularShows(page)
    }
}