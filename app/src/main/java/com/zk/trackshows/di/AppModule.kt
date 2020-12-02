/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zk.trackshows.di

import android.content.Context
import androidx.room.Room
import com.zk.trackshows.repository.*
import com.zk.trackshows.repository.local.ShowsDao
import com.zk.trackshows.repository.local.ShowsDatabase
import com.zk.trackshows.repository.local.ShowsLocalDataSource
import com.zk.trackshows.repository.network.ShowsRemoteDataSource
import com.zk.trackshows.repository.network.api.TvShowsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * As these types are scoped to the application lifecycle using @Singleton, they're installed
 * in Hilt's ApplicationComponent.
 */
@ExperimentalCoroutinesApi
@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Qualifier
    @Retention(RUNTIME)
    annotation class RemoteTasksDataSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class LocalTasksDataSource

    @Singleton
    @RemoteTasksDataSource
    @Provides
    fun provideTasksRemoteDataSource(
        service: TvShowsService,
        ioDispatcher: CoroutineDispatcher
    ): ShowsDataSource {
        return ShowsRemoteDataSource(
            service, ioDispatcher
        )
    }

    @Singleton
    @LocalTasksDataSource
    @Provides
    fun provideTasksLocalDataSource(
        database: ShowsDatabase,
        ioDispatcher: CoroutineDispatcher
    ): ShowsDataSource {
        return ShowsLocalDataSource(
            database.showDao(), ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ShowsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ShowsDatabase::class.java,
            "Shows.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideShowsDao(myDB: ShowsDatabase): ShowsDao {
        return myDB.showDao()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

/**
 * The binding for TasksRepository is on its own module so that we can replace it easily in tests.
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
object ShowsRepositoryModule {

    @Singleton
    @Provides
    fun provideShowsRepository(
        @AppModule.RemoteTasksDataSource remoteTasksDataSource: ShowsDataSource,
        @AppModule.LocalTasksDataSource localTasksDataSource: ShowsDataSource,
        ioDispatcher: CoroutineDispatcher
    ): ShowsRepository {
        return ShowsRepositoryImpl(
            remoteTasksDataSource, localTasksDataSource, ioDispatcher
        )
    }
}
