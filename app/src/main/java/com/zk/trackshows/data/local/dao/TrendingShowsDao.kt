package com.zk.trackshows.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zk.trackshows.data.local.model.TrendingShow
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Data Access Object for the tasks table.
 */
@ExperimentalCoroutinesApi
@Dao
interface TrendingShowsDao {

    @Query("SELECT * FROM TrendingShow ")
    fun trendingPagingSource(): PagingSource<Int, TrendingShow>

    @Query("SELECT * FROM TrendingShow ")
    fun trendingShows(): List<TrendingShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shows: List<TrendingShow>)

    @Query("DELETE FROM TrendingShow")
    suspend fun deleteShows()

}