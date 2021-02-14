package com.zk.trackshows.utils

import androidx.paging.PagingSource
import com.zk.trackshows.data.local.model.DataBaseShow
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.network.FakeTvShowsService

class MockPopularShowsPagingSource(private val mockData: List<PopularShow>): PagingSource<Int, PopularShow>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, PopularShow> {
        return LoadResult.Page(
            data = mockData,
            prevKey = null, // Only paging forward.
            nextKey = 1
        )
    }
}