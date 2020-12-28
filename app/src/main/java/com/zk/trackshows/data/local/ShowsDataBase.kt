package com.zk.trackshows.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zk.trackshows.data.local.dao.PopularShowsDao
import com.zk.trackshows.data.local.dao.TopRatedShowsDao
import com.zk.trackshows.data.local.dao.WatchListDao
import com.zk.trackshows.data.local.model.PopularShow
import com.zk.trackshows.data.local.model.ShowEntity
import com.zk.trackshows.data.local.model.TopRatedShow
import com.zk.trackshows.data.local.model.WatchedShow
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Database(
    entities = [
        ShowEntity::class,
        TopRatedShow::class,
        PopularShow::class,
        WatchedShow::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ShowsDatabase : RoomDatabase() {
    abstract fun topRatedShowsDao(): TopRatedShowsDao
    abstract fun popularShowsDao(): PopularShowsDao
    abstract fun watchListDao(): WatchListDao
}
