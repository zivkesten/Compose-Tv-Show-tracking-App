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

package com.zk.trackshows.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zk.trackshows.extensions.whenNotNull
import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.local.RemoteKeys
import com.zk.trackshows.repository.local.ShowsLocalDataSource
import com.zk.trackshows.repository.network.ShowsRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class ShowsRemoteMediator(
        localDataSource: ShowsDataSource,
        remoteDataSource: ShowsDataSource
) : RemoteMediator<Int, Show>() {

    val dataBase = localDataSource as ShowsLocalDataSource
    val service = remoteDataSource as ShowsRemoteDataSource

    @ExperimentalCoroutinesApi
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Show>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    return MediatorResult.Success(endOfPaginationReached = true)
                    //throw InvalidObjectException("Remote key and the prevKey should not be null")
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                    //throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }
        try {
            val apiResponse = service.getPagedPopularShows(page)

                    val shows = apiResponse.shows

                    val endOfPaginationReached = shows.isEmpty()
                    if (loadType == LoadType.REFRESH) {
                        dataBase.clearRemoteKeys()
                        dataBase.deleteAllShows()
                    }
                    val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1

                    val keys = shows.map {
                        RemoteKeys(showId = it.id, prevKey = prevKey, nextKey = nextKey)
                    }
                    keys.let { dataBase.insertAll(it) }
                    shows.let { dataBase.cacheShows(it) }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Show>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                ?.let { show ->
                    // Get the remote keys of the last item retrieved
                    dataBase.remoteKeysShowId(show.id)
                }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Show>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        val pages = state.pages
        val firstPageThatIsNotNullOrEmpty = pages.firstOrNull { it.data.isNotEmpty() }
        val shows = firstPageThatIsNotNullOrEmpty?.data
        val firstShow = shows?.firstOrNull()
        whenNotNull(firstShow) {
            return dataBase.remoteKeysShowId(it.id)
        }
        return null
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
            state: PagingState<Int, Show>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                dataBase.remoteKeysShowId(repoId)
            }
        }
    }
}