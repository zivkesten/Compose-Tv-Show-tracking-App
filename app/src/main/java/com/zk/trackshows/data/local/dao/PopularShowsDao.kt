package com.zk.trackshows.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zk.trackshows.data.local.model.PopularShow
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Data Access Object for the tasks table.
 */
@ExperimentalCoroutinesApi
@Dao
interface PopularShowsDao {

    @Query("SELECT * FROM PopularShow ")
    fun popularShowsPagingSource(): PagingSource<Int, PopularShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shows: List<PopularShow>): List<Long>

    @Query("DELETE FROM PopularShow")
    suspend fun deleteShows()

}