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

import com.zk.trackshows.repository.FakeShowsDataSource
import com.zk.trackshows.repository.ShowsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

/**
 * TasksRepository binding to use in tests.
 *
 * Hilt will inject a [FakeShowsRepository] instead of a [ShowsRepositoryImpl].
 */
@Module
@InstallIn(ApplicationComponent::class)
abstract class TestTasksRepositoryModule {
    @ExperimentalCoroutinesApi
    @Singleton
    @Binds
    abstract fun bindRepository(show: FakeShowsDataSource): ShowsRepository
}
