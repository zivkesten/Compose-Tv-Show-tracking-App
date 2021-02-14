package com.zk.trackshows.data.repositories

import androidx.paging.ExperimentalPagingApi
import com.zk.trackshows.data.DiscoverShowsLocalDataSource
import com.zk.trackshows.data.RemoteDataSource
import com.zk.trackshows.data.local.FakeDiscoverShowsLocalDataSource
import com.zk.trackshows.data.local.mapper.MockEntityMapper
import com.zk.trackshows.data.local.model.ShowEntity
import com.zk.trackshows.data.network.FakeRemoteDataSource
import com.zk.trackshows.data.network.mapper.MockDtoMapper
import com.zk.trackshows.data.network.model.ShowDto
import com.zk.trackshows.domain.mapper.DomainMapper
import com.zk.trackshows.domain.model.Show
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

@ExperimentalPagingApi
class DiscoverShowsRepositoryImplTest {

    private lateinit var sut: DiscoverShowsRepositoryImpl
    private lateinit var showsRemoteDataSource: RemoteDataSource
    private lateinit var showsDiscoverShowsLocalDataSource: DiscoverShowsLocalDataSource
    private lateinit var dtoMapper: DomainMapper<ShowDto, Show>
    private lateinit var entityMapper: DomainMapper<ShowEntity, Show>

    @Before
    fun setUp() {
        showsRemoteDataSource = FakeRemoteDataSource()
        showsDiscoverShowsLocalDataSource = FakeDiscoverShowsLocalDataSource()
        dtoMapper = MockDtoMapper()
        entityMapper = MockEntityMapper()
        sut = DiscoverShowsRepositoryImpl(
            showsRemoteDataSource,
            showsDiscoverShowsLocalDataSource,
            dtoMapper,
            entityMapper
        )
    }
}