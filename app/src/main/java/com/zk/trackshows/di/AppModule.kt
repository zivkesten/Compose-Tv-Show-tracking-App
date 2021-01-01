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
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import coil.ImageLoader
import coil.util.CoilUtils
import com.zk.trackshows.data.DiscoverShowsLocalDataSource
import com.zk.trackshows.data.RemoteDataSource
import com.zk.trackshows.data.WatchListLocalDataSource
import com.zk.trackshows.data.local.DiscoverShowsLocalDataSourceImpl
import com.zk.trackshows.data.local.ShowsDatabase
import com.zk.trackshows.data.local.WatchListLocalDataSourceImpl
import com.zk.trackshows.data.local.dao.PopularShowsDao
import com.zk.trackshows.data.local.mapper.ShowEntityMapper
import com.zk.trackshows.data.network.ShowsRemoteDataSource
import com.zk.trackshows.data.network.api.TvShowsService
import com.zk.trackshows.data.network.model.ShowDtoMapper
import com.zk.trackshows.data.repositories.DiscoverShowsRepository
import com.zk.trackshows.data.repositories.DiscoverShowsRepositoryImpl
import com.zk.trackshows.data.repositories.WatchListRepository
import com.zk.trackshows.data.repositories.WatchListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

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

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        service: TvShowsService
    ): RemoteDataSource {
        return ShowsRemoteDataSource(service)
    }

    @Singleton
    @Provides
    fun provideDiscoverShowsLocalDataSource(
        database: ShowsDatabase,
    ): DiscoverShowsLocalDataSource {
        return DiscoverShowsLocalDataSourceImpl(
            database.popularShowsDao(), database.topRatedShowsDao(), database.TrendingShowsDao()
        )
    }

    @Singleton
    @Provides
    fun provideWatchListLocalDataSource(
        database: ShowsDatabase,
        entityMapper: ShowEntityMapper
    ): WatchListLocalDataSource {
        return WatchListLocalDataSourceImpl(
            database.watchListDao(), entityMapper
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
    fun providePopularShowsDao(myDB: ShowsDatabase): PopularShowsDao {
        return myDB.popularShowsDao()
    }

    @Singleton
    @Provides
    fun provideShowsEntityMapper(): ShowEntityMapper {
        return ShowEntityMapper()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun provideImageLoader(context: Context) =
        ImageLoader.Builder(context)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(context))
                    .build()
            }
            .build()
}

/**
 * The binding for repositories is on its own module so that we can replace it easily in tests.
 */
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
object RepositoriesModule {

    @Singleton
    @Provides
    fun provideShowsRepository(
        watchListLocalDataSource: WatchListLocalDataSource,
    ): WatchListRepository {
        return WatchListRepositoryImpl(
            watchListLocalDataSource
        )
    }

    @Singleton
    @Provides
    fun provideDiscoverShowsRepository(
        remoteDataSource: RemoteDataSource,
        discoverShowsLocalDataSource: DiscoverShowsLocalDataSource,
        dtoMapper: ShowDtoMapper,
        entityMapper: ShowEntityMapper
    ): DiscoverShowsRepository {
        return DiscoverShowsRepositoryImpl(
            remoteDataSource, discoverShowsLocalDataSource, dtoMapper, entityMapper
        )
    }
}
