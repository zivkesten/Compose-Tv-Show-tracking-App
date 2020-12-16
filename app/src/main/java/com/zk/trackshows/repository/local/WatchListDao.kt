package com.zk.trackshows.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zk.trackshows.model.WatchedShow
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

    @Query("SELECT * FROM WatchedShow")
    suspend fun getShows(): List<WatchedShow>

    @Query("SELECT * FROM WatchedShow WHERE id = :showId")
    fun observeShow(showId: Int): Flow<WatchedShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shows: List<WatchedShow>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShow(show: WatchedShow)

    @Query("DELETE FROM WatchedShow")
    suspend fun deleteShows()

    @Query("DELETE FROM WatchedShow WHERE id = :showId")
    suspend fun deleteShow(showId: Int)

}