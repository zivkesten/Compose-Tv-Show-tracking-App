package com.zk.trackshows.data.repositories

import androidx.paging.PagingData
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.TopRatedShow
import com.zk.trackshows.data.local.model.WatchedShow
import kotlinx.coroutines.flow.Flow

interface DiscoverShowsRepository {

    fun popularShowsPagingData(forceUpdate: Boolean): Flow<PagingData<PopularShow>>

    fun topRatedShowsPagingData(forceUpdate: Boolean): Flow<PagingData<TopRatedShow>>

}