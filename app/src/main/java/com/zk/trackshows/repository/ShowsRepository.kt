package com.zk.trackshows.repository

import com.zk.trackshows.model.Show
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {

    suspend fun refreshPopularShows()

    suspend fun observePopularShows(forceUpdate: Boolean): Flow<Result<List<Show>>>

    suspend fun getPopularShows(forceUpdate: Boolean): Result<List<Show>?>

    suspend fun cacheShow(show: Show)

    suspend fun deleteAllShows()


}