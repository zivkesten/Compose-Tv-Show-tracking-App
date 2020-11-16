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

import com.zk.trackshows.model.Show
import com.zk.trackshows.repository.Result
import com.zk.trackshows.repository.ShowsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Concrete implementation of a data source as a db.
 */
@ExperimentalCoroutinesApi
class ShowsLocalDataSource internal constructor(
    private val showsDao: ShowsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ShowsDataSource {

    override fun observeShows(): Flow<Result<List<Show>>> {
        return showsDao.observeShows().map {
            Result.Success(it)
        }
    }

    override fun observeShow(): Flow<Result<Show>> {
        return showsDao.observeShow().map {
            Result.Success(it)
        }
    }

//    override fun observeTask(taskId: String): LiveData<Result<Task>> {
//        return tasksDao.observeTaskById(taskId).map {
//            Success(it)
//        }
//    }

//    override suspend fun refreshTask(taskId: String) {
//        // NO-OP
//    }
//
//    override suspend fun refreshTasks() {
//        // NO-OP
//    }

    override suspend fun getPopularShows(): Result<List<Show>?> {

        return try {
            Result.Success(showsDao.getShows())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
//
    override suspend fun cacheShows(show: Show) {
        showsDao.insertShow(show)
    }

    override suspend fun deleteAllShows() {
        showsDao.deleteShows()
    }

    override suspend fun refreshPopularShows() {
        // NO-OP
    }
}

