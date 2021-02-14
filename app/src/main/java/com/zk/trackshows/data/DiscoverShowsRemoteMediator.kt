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

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zk.trackshows.data.local.mapper.ShowEntityMapper
import com.zk.trackshows.data.local.model.ShowEntity
import com.zk.trackshows.data.network.api.TvShowResponse
import com.zk.trackshows.data.network.mapper.ShowDtoMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class DiscoverShowsRemoteMediator @Inject constructor(
    private val dtoMapper: ShowDtoMapper,
    private val entityMapper: ShowEntityMapper,
    private val apiRequest: suspend (page: Int) -> TvShowResponse,
    private val clearCache: suspend () -> Unit,
    private val cacheData: suspend (List<ShowEntity>) -> Unit,
    private val mapToEntity: (ShowEntity) -> Any
) : RemoteMediator<Int, ShowEntity>() {

    private var page: Int = STARTING_PAGE_INDEX

    @ExperimentalCoroutinesApi
    override suspend fun load(loadType: LoadType, state: PagingState<Int, ShowEntity>): MediatorResult {
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
            Log.w("Zivi", "service.getPagedPopularShows($page)")
            val apiResponse = apiRequest.invoke(page)

                    page = apiResponse.page
                    val shows = apiResponse.shows

                    val endOfPaginationReached = shows.isEmpty()
                    if (loadType == LoadType.REFRESH) {
                        Log.w("Zivi", "deleteAllShows")
                        clearCache.invoke()
                    }

                    shows.let { showDtos ->
                        Log.w("Zivi", "cacheShows")
                        val domainModels = dtoMapper.toDomainList(showDtos)
                        val entities = entityMapper.fromDomainList(domainModels)
                        val trs = entities.map { mapToEntity.invoke(it) }
                        cacheData.invoke(trs as List<ShowEntity>)
                    }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}