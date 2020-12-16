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

package com.zk.trackshows.repository

import androidx.paging.PagingSource
import com.zk.trackshows.model.Show
import com.zk.trackshows.model.WatchedShow
import com.zk.trackshows.repository.local.RemoteKeys
import com.zk.trackshows.repository.network.api.TvShowResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class FakeDataSource(var shows: MutableList<Show?> = mutableListOf()) : ShowsDataSource {
    override suspend fun observeWatchList(): Flow<List<Show>> {
        TODO("Not yet implemented")
    }

    override fun observeWatchedShow(showId: Int): Flow<WatchedShow> {
        TODO("Not yet implemented")
    }

    override fun observePagedShows(): PagingSource<Int, Show> {
        TODO("Not yet implemented")
    }

    override fun observeShows(): Flow<Result<List<Show>>> {
        TODO("Not yet implemented")
    }

    override fun observeShow(showId: String): Flow<Result<Show>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularShows(): List<Show>? {
        TODO("Not yet implemented")
    }

    override suspend fun getPagedPopularShows(page: Int): TvShowResponse {
        TODO("Not yet implemented")
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
//    override suspend fun getTasks(): Result<List<Show>> {
//        tasks?.let { return Success(ArrayList(it)) }
//        return Error(
//            Exception("Tasks not found")
//        )
//    }
//
//    override suspend fun getTask(taskId: String): Result<Task> {
//        tasks?.firstOrNull { it.id == taskId }?.let { return Success(it) }
//        return Error(
//            Exception("Task not found")
//        )
//    }
//
//    override suspend fun saveTask(task: Task) {
//        tasks?.add(task)
//    }
//
//    override suspend fun completeTask(task: Task) {
//        tasks?.firstOrNull { it.id == task.id }?.let { it.isCompleted = true }
//    }
//
//    override suspend fun completeTask(taskId: String) {
//        tasks?.firstOrNull { it.id == taskId }?.let { it.isCompleted = true }
//    }
//
//    override suspend fun activateTask(task: Task) {
//        tasks?.firstOrNull { it.id == task.id }?.let { it.isCompleted = false }
//    }
//
//    override suspend fun activateTask(taskId: String) {
//        tasks?.firstOrNull { it.id == taskId }?.let { it.isCompleted = false }
//    }
//
//    override suspend fun clearCompletedTasks() {
//        tasks?.removeIf { it.isCompleted }
//    }
//
//    override suspend fun deleteAllTasks() {
//        tasks?.clear()
//    }
//
//    override suspend fun deleteTask(taskId: String) {
//        tasks?.removeIf { it.id == taskId }
//    }
//
//    override fun observeTasks(): LiveData<Result<List<Task>>> {
//        TODO("not implemented")
//    }
//
//    override suspend fun refreshTasks() {
//        TODO("not implemented")
//    }
//
//    override fun observeTask(taskId: String): LiveData<Result<Task>> {
//        TODO("not implemented")
//    }
//
//    override suspend fun refreshTask(taskId: String) {
//        TODO("not implemented")
//    }
}
