package com.zk.trackshows.data.repositories

import androidx.paging.PagingData
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.TopRatedShow
import com.zk.trackshows.data.local.model.TrendingShow
import com.zk.trackshows.data.local.model.WatchedShow
import com.zk.trackshows.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface DiscoverShowsRepository {

    fun popularShowsPagingData(): Flow<PagingData<Show>>

    fun topRatedShowsPagingData(): Flow<PagingData<Show>>

    fun trendingShowsPagingData(): Flow<PagingData<Show>>

}