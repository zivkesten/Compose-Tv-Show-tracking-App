package com.zk.trackshows.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zk.trackshows.data.local.dao.PopularShowsDao
import com.zk.trackshows.data.local.dao.TopRatedShowsDao
import com.zk.trackshows.data.local.dao.TrendingShowsDao
import com.zk.trackshows.data.local.dao.WatchListDao
import com.zk.trackshows.data.local.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Database(
    entities = [
        ShowEntity::class,
        TopRatedShow::class,
        PopularShow::class,
        WatchedShow::class,
        TrendingShow::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ShowsDatabase : RoomDatabase() {
    abstract fun topRatedShowsDao(): TopRatedShowsDao
    abstract fun popularShowsDao(): PopularShowsDao
    abstract fun watchListDao(): WatchListDao
    abstract fun TrendingShowsDao(): TrendingShowsDao
}
