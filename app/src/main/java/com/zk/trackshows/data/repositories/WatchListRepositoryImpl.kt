package com.zk.trackshows.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zk.trackshows.data.LocalDataSource
import com.zk.trackshows.data.PopularShowsRemoteMediator
import com.zk.trackshows.data.RemoteDataSource
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.ShowEntityMapper
import com.zk.trackshows.data.local.model.WatchedShow
import com.zk.trackshows.data.network.model.ShowDtoMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class WatchListRepositoryImpl (
    private val showsLocalDataSource: LocalDataSource,
) : WatchListRepository {

    override suspend fun addToWatchList(show: WatchedShow) {
        showsLocalDataSource.addToWatchList(show)
    }

    override suspend fun observeWatchList(): Flow<List<WatchedShow>> {
        return showsLocalDataSource.observeWatchedShows()
    }

    override suspend fun removeFromWatchList(showId: Int) {
        return showsLocalDataSource.removeFromWatchList(showId)
    }

}