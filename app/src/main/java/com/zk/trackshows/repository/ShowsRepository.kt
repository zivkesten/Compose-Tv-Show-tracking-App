package com.zk.trackshows.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.zk.trackshows.model.Show
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {

    suspend fun refreshPopularShows()

    suspend fun observePopularShows(forceUpdate: Boolean): PagingSource<Int, Show>

    suspend fun getPopularShows(forceUpdate: Boolean): Result<List<Show>?>

    suspend fun cacheShow(show: Show)

    suspend fun deleteAllShows()


}