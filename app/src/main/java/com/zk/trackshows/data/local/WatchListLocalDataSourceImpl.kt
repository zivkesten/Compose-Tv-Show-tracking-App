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

import com.zk.trackshows.data.WatchListLocalDataSource
import com.zk.trackshows.data.local.dao.WatchListDao
import com.zk.trackshows.data.local.mapper.ShowEntityMapper
import com.zk.trackshows.data.local.model.ShowEntity
import com.zk.trackshows.data.local.model.WatchedShow
import com.zk.trackshows.domain.model.Show
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Concrete implementation of a data source as a db.
 */
@ExperimentalCoroutinesApi
class WatchListLocalDataSourceImpl internal constructor(
    private val watchListDao: WatchListDao,
    private val entityMapper: ShowEntityMapper
): WatchListLocalDataSource {

    override suspend fun observeWatchedShows(): Flow<List<Show>> {
        return watchListDao.observeShows().mapWatchShowListFlowToShowListFlow(entityMapper)
    }

    override fun getWatchList(): List<Show> {
        return watchListDao.getShows().mapWatchShowListToShowList(entityMapper)
    }

    override suspend fun addToWatchList(show: Show) {
        watchListDao.insertShow(WatchedShow(entityMapper.mapFromDomainModel(show)))
    }

    override suspend fun removeFromWatchList(showId: Int) {
        watchListDao.deleteShow(showId)
    }

    private fun Flow<List<WatchedShow>>.mapWatchShowListFlowToShowListFlow(entityMapper: ShowEntityMapper): Flow<List<Show>> {
        return this.mapToShowEntityList().map { showEntities -> entityMapper.toDomainList(showEntities) }
    }

    private fun Flow<List<WatchedShow>>.mapToShowEntityList(): Flow<List<ShowEntity>> {
        return this.map { it.map { watchedShow -> watchedShow.show } }
    }

    private fun List<WatchedShow>.mapWatchShowListToShowList(entityMapper: ShowEntityMapper): List<Show> {
        return mapWatchedShowToShowEntity().map { showEntity -> entityMapper.mapToDomainModel(showEntity) }
    }

    private fun List<WatchedShow>.mapWatchedShowToShowEntity() = this.map { shows -> shows.show }
}



