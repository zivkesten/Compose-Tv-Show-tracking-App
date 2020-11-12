package com.zk.trackshows.repository

import androidx.lifecycle.LiveData
import com.zk.trackshows.model.PopularShows
import com.zk.trackshows.model.Show
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
interface ShowsDataSource {

    fun observeShows(): Flow<Result<List<Show>>>

    fun observeShow(): Flow<Result<Show>>

    suspend fun getShows(): Result<List<Show>>

    suspend fun cacheShows(show: Show)

    suspend fun deleteAllShows()

    suspend fun refreshPopularShows()
}