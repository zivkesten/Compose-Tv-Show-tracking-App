package com.zk.trackshows.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zk.trackshows.repository.local.model.PopularShow
import com.zk.trackshows.repository.local.model.WatchedShow
import com.zk.trackshows.repository.local.model.ShowEntityMapper
import com.zk.trackshows.repository.network.model.ShowDtoMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class ShowsRepositoryImpl (
    private val showsRemoteDataSource: RemoteDataSource,
    private val showsLocalDataSource: LocalDataSource,
    private val dtoMapper: ShowDtoMapper,
    private val entityMapper: ShowEntityMapper
) : ShowsRepository {

    override fun popularShowsPagingData(forceUpdate: Boolean): Flow<PagingData<PopularShow>> {

        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = true),
            remoteMediator = PopularShowsRemoteMediator(
                showsLocalDataSource,
                showsRemoteDataSource,
                dtoMapper,
                entityMapper
            ),
            pagingSourceFactory = showsLocalDataSource::observePagedPopularShows
        ).flow
    }

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