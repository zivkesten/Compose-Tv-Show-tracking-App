package com.zk.trackshows.repository

import com.zk.trackshows.model.Show
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {

    fun observePopularShows(): Flow<Result<List<Show>>>

    suspend fun getShows(forceUpdate: Boolean): Result<List<Show>?>

    suspend fun cacheShow(show: Show)

    suspend fun deleteAllShows()

    suspend fun refreshPopularShows()

}