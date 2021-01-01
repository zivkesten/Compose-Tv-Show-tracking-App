package com.zk.trackshows.data.repositories

import androidx.paging.*
import com.zk.trackshows.data.*
import com.zk.trackshows.data.local.mapper.ShowEntityMapper
import com.zk.trackshows.data.local.model.*
import com.zk.trackshows.data.network.model.ShowDtoMapper
import com.zk.trackshows.domain.model.Show
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class DiscoverShowsRepositoryImpl (
    private val showsRemoteDataSource: RemoteDataSource,
    private val showsDiscoverShowsLocalDataSource: DiscoverShowsLocalDataSource,
    private val dtoMapper: ShowDtoMapper,
    private val entityMapper: ShowEntityMapper
) : DiscoverShowsRepository {

    override fun popularShowsPagingData(): Flow<PagingData<Show>> {

        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = true),
            remoteMediator = PopularShowsRemoteMediator(
                showsDiscoverShowsLocalDataSource,
                showsRemoteDataSource,
                dtoMapper,
                entityMapper
            ),
            pagingSourceFactory = showsDiscoverShowsLocalDataSource::observePagedPopularShows
        )
             //Map pager to flow of domain models
            .flow.mapEntityListToShowList(entityMapper)
    }

    override fun topRatedShowsPagingData(): Flow<PagingData<Show>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = true),
            remoteMediator = TopRatedRemoteMediator(
                showsDiscoverShowsLocalDataSource,
                showsRemoteDataSource,
                dtoMapper,
                entityMapper
            ),
            pagingSourceFactory = showsDiscoverShowsLocalDataSource::observePagedTopRatedShows
        ).flow.mapEntityListToShowList(entityMapper)
    }

    override fun trendingShowsPagingData(): Flow<PagingData<Show>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = true),
            remoteMediator = TrendingShowsRemoteMediator (
                showsDiscoverShowsLocalDataSource,
                showsRemoteDataSource,
                dtoMapper,
                entityMapper
            ),
            pagingSourceFactory = showsDiscoverShowsLocalDataSource::observePagedTrendingShows
        ).flow.mapEntityListToShowList(entityMapper)
    }

    private fun <T: DataBaseShow> Flow<PagingData<T>>.mapEntityListToShowList(entityMapper: ShowEntityMapper): Flow<PagingData<Show>> {
        return this.mapToShowEntityList().map { entities -> entities.map { entity -> entityMapper.mapToDomainModel(entity) } }
    }

    private fun <T: DataBaseShow> Flow<PagingData<T>>.mapToShowEntityList(): Flow<PagingData<ShowEntity>> {
        return this.map { popularShowsList -> popularShowsList.map { popularShow -> popularShow.entity() } }
    }
}