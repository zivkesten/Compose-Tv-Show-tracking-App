package com.zk.trackshows.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.zk.trackshows.model.Show
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
interface ShowsDataSource {

    fun observePagedShows(): PagingSource<Int, Show>

    fun observeShows(): Flow<Result<List<Show>>>

    fun observeShow(showId: String): Flow<Result<Show>>

    suspend fun getPopularShows(): Result<List<Show>?>

    suspend fun cacheShow(show: Show)

    suspend fun cacheShows(shows: List<Show>)

    suspend fun deleteAllShows()

    suspend fun refreshPopularShows()

}