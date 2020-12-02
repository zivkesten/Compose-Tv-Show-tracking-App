package com.zk.trackshows.repository

import com.zk.trackshows.common.InfoLogger.logMessage
import com.zk.trackshows.model.Show
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class ShowsRepositoryImpl (
    private val showsRemoteDataSource: ShowsDataSource,
    private val showsLocalDataSource: ShowsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ShowsRepository {

    private val _observePopularShows= MutableStateFlow<Result<List<Show>?>>(Result.Loading)
    private val observePopularShows: StateFlow<Result<List<Show>?>> get() = _observePopularShows

    override suspend fun observePopularShows(forceUpdate: Boolean): Flow<Result<List<Show>>> {
        if (forceUpdate) {
            refreshPopularShows()
        }
        return showsLocalDataSource.observeShows()
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
            showsLocalDataSource.getPopularShows()
        }
    }

    private suspend fun updatePopularShowsFromRemoteDataSource() {
        val remoteShows = showsRemoteDataSource.getPopularShows()
        if (remoteShows is Result.Success) {
            showsLocalDataSource.deleteAllShows()
            remoteShows.data?.forEach { show ->
                showsLocalDataSource.cacheShows(show)
            }
        } else if (remoteShows is Result.Error) {
            throw remoteShows.exception
        }
    }

    override suspend fun cacheShow(show: Show) {
        showsLocalDataSource.cacheShows(show)
    }

    override suspend fun deleteAllShows() {
        showsLocalDataSource.deleteAllShows()
    }


}