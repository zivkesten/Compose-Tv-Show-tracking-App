package com.zk.trackshows.repository

import com.zk.trackshows.model.Show
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
interface ShowsDataSource {

    fun observeShows(): Flow<Result<List<Show>>>

    fun observeShow(): Flow<Result<Show>>

    suspend fun getPopularShows(): Result<List<Show>?>

    suspend fun cacheShows(show: Show)

    suspend fun deleteAllShows()

    suspend fun refreshPopularShows()
}