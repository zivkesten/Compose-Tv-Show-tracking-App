package com.zk.trackshows.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zk.trackshows.common.InfoLogger.logMessage
import com.zk.trackshows.model.Show
import com.zk.trackshows.model.WatchedShow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class ShowsRepositoryImpl (
    private val showsRemoteDataSource: ShowsDataSource,
    private val showsLocalDataSource: ShowsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ShowsRepository {

    override fun observePagedPopularShows(forceUpdate: Boolean): Flow<PagingData<Show>> {

        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = true),
            remoteMediator = ShowsRemoteMediator(
                showsLocalDataSource,
                showsRemoteDataSource
            ),
            pagingSourceFactory = showsLocalDataSource::observePagedShows
        ).flow
    }

    override suspend fun refreshPopularShows() {
        try {
            updatePopularShowsFromRemoteDataSource()
        } catch (ex: Exception) {
            logMessage("Error refreshing data: ${ex.localizedMessage}")
        }
    }

    override suspend fun getPopularShows(forceUpdate: Boolean): Result<List<Show>?> {

       return withContext(ioDispatcher) {
            if (forceUpdate) {
                try {
                    updatePopularShowsFromRemoteDataSource()
                } catch (ex: Exception) {
                    Result.Error(ex)
                }
            }
            Result.Success(showsLocalDataSource.getPopularShows())
        }
    }

    private suspend fun updatePopularShowsFromRemoteDataSource() {
        val remoteShows = showsRemoteDataSource.getPopularShows()
        showsLocalDataSource.deleteAllShows()
        remoteShows?.let { shows ->
            showsLocalDataSource.cacheShows(shows)
        }
    }

    override suspend fun cacheShow(show: Show) {
        showsLocalDataSource.cacheShow(show)
    }

    override suspend fun addToWatchList(show: WatchedShow) {
        showsLocalDataSource.addToWatchList(show)
    }

    override suspend fun observeWatchedShow(showId: Int): Flow<WatchedShow> {
        return showsLocalDataSource.observeWatchedShow(showId)
    }

    override suspend fun observeWatchList(): Flow<List<Show>> {
        return showsLocalDataSource.observeWatchList()
    }

    override suspend fun removeFromWatchList(showId: Int) {
        return showsLocalDataSource.removeFromWatchList(showId)
    }

    override suspend fun deleteAllShows() {
        showsLocalDataSource.deleteAllShows()
    }


}