package com.zk.trackshows.repository

import androidx.paging.PagingData
import com.zk.trackshows.data.repositories.DiscoverShowsRepository
import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.utils.mockPagingDataOfPopularShows
import com.zk.trackshows.utils.mockPagingDataOfShows
import com.zk.trackshows.utils.mockTopRatedShows
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class FakeDiscoverShowsRepository: DiscoverShowsRepository {

    override fun popularShowsPagingData(): Flow<PagingData<Show>> {
        return flow { emit(mockPagingDataOfShows) }
    }

    override fun topRatedShowsPagingData(): Flow<PagingData<Show>> {
        return flow { emit(mockPagingDataOfShows) }
    }

    override fun trendingShowsPagingData(): Flow<PagingData<Show>> {
        return flow { emit(mockPagingDataOfShows) }
    }

}