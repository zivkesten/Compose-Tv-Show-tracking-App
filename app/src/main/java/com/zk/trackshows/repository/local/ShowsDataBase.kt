package com.zk.trackshows.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zk.trackshows.model.Show
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Database(entities = [Show::class], version = 1, exportSchema = false)
abstract class ShowsDatabase : RoomDatabase() {
    abstract fun showDao(): ShowsDao
}
