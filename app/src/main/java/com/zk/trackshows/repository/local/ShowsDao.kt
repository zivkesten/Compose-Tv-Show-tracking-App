package com.zk.trackshows.repository.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zk.trackshows.model.Show
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the tasks table.
 */
@ExperimentalCoroutinesApi
@Dao
interface ShowsDao {

    @Query("SELECT * FROM show ")
    fun showsPagingSource(): PagingSource<Int, Show>

    @Query("SELECT * FROM Show")
    fun observeShows(): Flow<List<Show>>

    @Query("SELECT * FROM Show")
    suspend fun getShows(): List<Show>

    @Query("SELECT * FROM Show WHERE id = :showId")
    fun observeShowById(showId: String): Flow<Show>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Show>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShow(show: Show)

    @Query("DELETE FROM Show")
    suspend fun deleteShows()

}