package com.zk.trackshows.data.local.dao

import com.zk.trackshows.data.local.model.WatchedShow
import com.zk.trackshows.utils.mockWatchedShow
import com.zk.trackshows.utils.mockWatchedShows
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWatchListDao: WatchListDao {

    override fun observeShows(): Flow<List<WatchedShow>> {
        return flow { emit(mockWatchedShows) }
    }

    override fun observeShow(showId: Int): Flow<WatchedShow> {
        return flow { emit(mockWatchedShow(1)) }
    }

    override fun getShows(): List<WatchedShow> {
        return mockWatchedShows
    }

    override suspend fun insertAll(shows: List<WatchedShow>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertShow(show: WatchedShow) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteShows() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteShow(showId: Int) {
        TODO("Not yet implemented")
    }
}