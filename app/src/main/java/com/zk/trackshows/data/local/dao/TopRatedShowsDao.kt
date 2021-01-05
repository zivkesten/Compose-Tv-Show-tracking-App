package com.zk.trackshows.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zk.trackshows.data.local.model.TopRatedShow
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Data Access Object for the tasks table.
 */
@ExperimentalCoroutinesApi
@Dao
interface TopRatedShowsDao {

    @Query("SELECT * FROM TopRatedShow ")
    fun topRatedPagingSource(): PagingSource<Int, TopRatedShow>

    @Query("SELECT * FROM TopRatedShow ")
    fun topRatedShows(): List<TopRatedShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<TopRatedShow>)

    @Query("DELETE FROM TopRatedShow")
    suspend fun deleteShows()

}