package com.zk.trackshows.data.local.dao

import androidx.room.*
import com.zk.trackshows.data.local.model.WatchedShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the tasks table.
 */
@ExperimentalCoroutinesApi
@Dao
interface WatchListDao {

    @Query("SELECT * FROM WatchedShow")
    fun observeShows(): Flow<List<WatchedShow>>

    @Query("SELECT * FROM WatchedShow WHERE id = :showId")
    fun observeShow(showId: Int): Flow<WatchedShow>

    @Query("SELECT * FROM WatchedShow")
    fun getShows(): List<WatchedShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shows: List<WatchedShow>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShow(show: WatchedShow)

    @Query("DELETE FROM WatchedShow")
    suspend fun deleteShows()

    @Query("DELETE FROM WatchedShow WHERE id = :showId")
    suspend fun deleteShow(showId: Int)

}