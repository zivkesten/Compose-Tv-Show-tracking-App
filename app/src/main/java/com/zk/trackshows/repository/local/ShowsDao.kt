package com.zk.trackshows.repository.local

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

//    @Query("SELECT * FROM show ")
//    fun showsPagingSource(): PagingSource<Int, List<Show>>

    @Query("SELECT * FROM Show")
    fun observeShows(): Flow<List<Show>>

    @Query("SELECT * FROM Show")
    fun observeShow(): Flow<Show>

    @Query("SELECT * FROM Show")
    fun getShows(): List<Show>

    /**
     * Observes a single task.
     *
     * @param showId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM Show WHERE id = :showId")
    fun observeShowById(showId: String): Flow<Show>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShow(show: Show)

    @Query("DELETE FROM Show")
    suspend fun deleteShows()

}