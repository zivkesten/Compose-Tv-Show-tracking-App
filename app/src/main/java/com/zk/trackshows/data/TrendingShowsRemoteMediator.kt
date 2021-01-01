/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.zk.trackshows.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zk.trackshows.data.local.mapper.ShowEntityMapper
import com.zk.trackshows.data.local.model.TrendingShow
import com.zk.trackshows.data.network.model.ShowDtoMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class TrendingShowsRemoteMediator(
    val dataBaseDiscoverShows: DiscoverShowsLocalDataSource,
    val service: RemoteDataSource,
    private val dtoMapper: ShowDtoMapper,
    private val entityMapper: ShowEntityMapper
) : RemoteMediator<Int, TrendingShow>() {

    private var page: Int = STARTING_PAGE_INDEX

    @ExperimentalCoroutinesApi
    override suspend fun load(loadType: LoadType, state: PagingState<Int, TrendingShow>): MediatorResult {
        page = when (loadType) {
            LoadType.REFRESH -> { STARTING_PAGE_INDEX }
            LoadType.PREPEND -> {
                if (page > STARTING_PAGE_INDEX) {
                    page - 1
                } else {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            LoadType.APPEND -> { page + 1 }
        }
        try {
            val apiResponse = service.fetchPagedTrendingShows(page)

                    page = apiResponse.page
                    val shows = apiResponse.shows

                    val endOfPaginationReached = shows.isEmpty()
                    if (loadType == LoadType.REFRESH) {
                        dataBaseDiscoverShows.clearTrendingShowsCache()
                    }

                    shows.let { showDtos ->
                        val domainModels = dtoMapper.toDomainList(showDtos)
                        val entities = entityMapper.fromDomainList(domainModels)
                        dataBaseDiscoverShows.cacheTrendingShows(entities.map { TrendingShow(it)})
                    }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}