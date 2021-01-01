package com.zk.trackshows.data.repositories

import androidx.paging.ExperimentalPagingApi
import com.zk.trackshows.data.WatchListLocalDataSource
import com.zk.trackshows.data.local.model.WatchedShow
import com.zk.trackshows.domain.model.Show
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class WatchListRepositoryImpl (
    private val watchListLocalDataSource: WatchListLocalDataSource
) : WatchListRepository {

    override suspend fun addToWatchList(show: Show) {
        watchListLocalDataSource.addToWatchList(show)
    }

    override suspend fun observeWatchList(): Flow<List<Show>> {
        return watchListLocalDataSource.observeWatchedShows()
    }

    override fun getWatchList(): List<Show> {
        return watchListLocalDataSource.getWatchList()
    }

    override suspend fun removeFromWatchList(showId: Int) {
        return watchListLocalDataSource.removeFromWatchList(showId)
    }

}