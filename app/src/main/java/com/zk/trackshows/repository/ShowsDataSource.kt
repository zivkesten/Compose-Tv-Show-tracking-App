package com.zk.trackshows.repository

import androidx.paging.PagingSource
import com.zk.trackshows.model.Show
import com.zk.trackshows.model.WatchedShow
import com.zk.trackshows.repository.local.RemoteKeys
import com.zk.trackshows.repository.network.api.TvShowResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

@ExperimentalCoroutinesApi
interface ShowsDataSource {

    suspend fun observeWatchList(): Flow<List<Show>>

    fun observeWatchedShow(showId: Int): Flow<WatchedShow>

    fun observePagedShows(): PagingSource<Int, Show>

    fun observeShows(): Flow<Result<List<Show>>>

    fun observeShow(showId: String): Flow<Result<Show>>

    suspend fun getPopularShows(): List<Show>?

    suspend fun getPagedPopularShows(@Query("page") page: Int): TvShowResponse

    suspend fun cacheShow(show: Show)

    suspend fun getKeys(): List<RemoteKeys>?

    suspend fun cacheShows(shows: List<Show>)

    suspend fun deleteAllShows()

    suspend fun refreshPopularShows()

    suspend fun addToWatchList(show: WatchedShow)

    suspend fun removeFromWatchList(showId: Int)


}