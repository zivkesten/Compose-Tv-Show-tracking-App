package com.zk.trackshows.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zk.trackshows.data.LocalDataSource
import com.zk.trackshows.data.PopularShowsRemoteMediator
import com.zk.trackshows.data.RemoteDataSource
import com.zk.trackshows.data.TopRatedRemoteMediator
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.ShowEntityMapper
import com.zk.trackshows.data.local.model.TopRatedShow
import com.zk.trackshows.data.local.model.WatchedShow
import com.zk.trackshows.data.network.model.ShowDtoMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class DiscoverShowsRepositoryImpl (
    private val showsRemoteDataSource: RemoteDataSource,
    private val showsLocalDataSource: LocalDataSource,
    private val dtoMapper: ShowDtoMapper,
    private val entityMapper: ShowEntityMapper
) : DiscoverShowsRepository {

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

    override fun topRatedShowsPagingData(forceUpdate: Boolean): Flow<PagingData<TopRatedShow>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = true),
            remoteMediator = TopRatedRemoteMediator(
                showsLocalDataSource,
                showsRemoteDataSource,
                dtoMapper,
                entityMapper
            ),
            pagingSourceFactory = showsLocalDataSource::observePagedTopRatedShows
        ).flow
    }
}