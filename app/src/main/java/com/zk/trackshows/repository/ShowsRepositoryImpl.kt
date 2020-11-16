package com.zk.trackshows.repository

import com.zk.trackshows.model.Show
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class ShowsRepositoryImpl (
    private val showsRemoteDataSource: ShowsDataSource,
    private val showsLocalDataSource: ShowsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ShowsRepository {

    private val observableShows= MutableStateFlow<Result<List<Show>?>>(Result.Loading)

    override fun observePopularShows(): Flow<Result<List<Show>>> {
        return showsLocalDataSource.observeShows()
    }

    override suspend fun refreshPopularShows() {
        observableShows.value = getShows(true)
    }

    override suspend fun getShows(forceUpdate: Boolean): Result<List<Show>?> {

       return withContext(ioDispatcher) {
            if (forceUpdate) {
                try {
                    updateShowsFromRemoteDataSource()
                } catch (ex: Exception) {
                    Result.Error(ex)
                }
            }
            showsLocalDataSource.getPopularShows()
        }
    }

    override suspend fun cacheShow(show: Show) {
        showsLocalDataSource.cacheShows(show)
    }

    override suspend fun deleteAllShows() {
        showsLocalDataSource.deleteAllShows()
    }


    private suspend fun updateShowsFromRemoteDataSource() {
        val remoteShows = showsRemoteDataSource.getPopularShows()
        if (remoteShows is Result.Success) {
            // Real apps might want to do a proper sync, deleting, modifying or adding each task.
            showsLocalDataSource.deleteAllShows()
            remoteShows.data?.forEach { show ->
                showsLocalDataSource.cacheShows(show)
            }
        } else if (remoteShows is Result.Error) {
            throw remoteShows.exception
        }
    }
}