package com.zk.trackshows.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zk.trackshows.repository.local.dao.PopularShowsDao
import com.zk.trackshows.repository.local.dao.TopRatedShowsDao
import com.zk.trackshows.repository.local.dao.WatchListDao
import com.zk.trackshows.repository.local.model.PopularShow
import com.zk.trackshows.repository.local.model.WatchedShow
import com.zk.trackshows.repository.local.model.ShowEntity
import com.zk.trackshows.repository.local.model.TopRatedShow
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Database(
    entities = [
        ShowEntity::class,
        TopRatedShow::class,
        PopularShow::class,
        WatchedShow::class],
    version = 1,
    exportSchema = false
)
abstract class ShowsDatabase : RoomDatabase() {
    abstract fun topRatedShowsDao(): TopRatedShowsDao
    abstract fun popularShowsDao(): PopularShowsDao
    abstract fun watchListDao(): WatchListDao
}
