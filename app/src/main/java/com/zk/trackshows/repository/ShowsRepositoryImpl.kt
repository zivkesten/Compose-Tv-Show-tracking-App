package com.zk.trackshows.repository

import android.util.Log
import com.zk.trackshows.model.Show
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class ShowsRepositoryImpl (
    private val showsRemoteDataSource: ShowsDataSource,
    private val showsLocalDataSource: ShowsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ShowsRepository {

    private val observableShows= MutableStateFlow<Result<List<Show>>>(Result.Loading)

    override fun observeShows(): Flow<Result<List<Show>>> {
        return showsLocalDataSource.observeShows()
    }
//
    override suspend fun refreshPopularShows() {
        observableShows.value = getShows(true)
    }

    override suspend fun getShows(forceUpdate: Boolean): Result<List<Show>> {

        if (forceUpdate) {
            try {
                updateShowsFromRemoteDataSource()
            } catch (ex: Exception) {
                return Result.Error(ex)
            }
        }
        return showsLocalDataSource.getShows()
    }

    override suspend fun cacheShow(show: Show) {
        showsLocalDataSource.cacheShows(show)
    }

    override suspend fun deleteAllShows() {
        showsLocalDataSource.deleteAllShows()
    }


    private suspend fun updateShowsFromRemoteDataSource() {
        val remoteShows = showsRemoteDataSource.getShows()

        if (remoteShows is Result.Success) {
            // Real apps might want to do a proper sync, deleting, modifying or adding each task.
            showsLocalDataSource.deleteAllShows()
            remoteShows.data.forEach { show ->
                showsLocalDataSource.cacheShows(show)
            }
        } else if (remoteShows is Result.Error) {
            throw remoteShows.exception
        }
    }
}