package com.zk.trackshows.repository

import androidx.paging.PagingSource
import com.zk.trackshows.model.Show
import com.zk.trackshows.model.WatchedShow
import com.zk.trackshows.repository.local.RemoteKeys
import com.zk.trackshows.repository.network.api.TvShowResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class FakeShowsDataSource: ShowsDataSource {

    //val mockedShows = List<Show>(Show.mock(), Show.mock(), Show.mock())
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
        return flow {
            emit(Result.Success(listOf(Show.mock())))
        }
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

}