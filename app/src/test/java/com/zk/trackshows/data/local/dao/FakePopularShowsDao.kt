package com.zk.trackshows.data.local.dao

import androidx.paging.PagingSource
import com.zk.trackshows.data.local.model.PopularShow

class FakePopularShowsDao: PopularShowsDao {
    override fun popularShowsPagingSource(): PagingSource<Int, PopularShow> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(shows: List<PopularShow>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteShows() {
        TODO("Not yet implemented")
    }
}