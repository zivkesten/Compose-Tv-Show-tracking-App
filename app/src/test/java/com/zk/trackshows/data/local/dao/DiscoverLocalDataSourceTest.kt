package com.zk.trackshows.data.local.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.zk.trackshows.data.WatchListLocalDataSource
import com.zk.trackshows.data.local.DiscoverShowsLocalDataSourceImpl
import com.zk.trackshows.data.local.ShowsDatabase
import com.zk.trackshows.utils.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Integration test for the [WatchListLocalDataSource].
 */
@InternalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class DiscoverLocalDataSourceTest {

    private lateinit var sut: DiscoverShowsLocalDataSourceImpl
    private lateinit var testDatabase: ShowsDatabase

    private lateinit var testDispatcher: TestCoroutineDispatcher
    private lateinit var testScope: TestCoroutineScope

    @Before
    fun setup() {
        testDispatcher = TestCoroutineDispatcher()
        testScope = TestCoroutineScope(TestCoroutineDispatcher())
        val context = InstrumentationRegistry.getInstrumentation().context
        // using an in-memory database for testing, since it doesn't survive killing the process
        testDatabase = Room.inMemoryDatabaseBuilder(
            context,
            ShowsDatabase::class.java
        )
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .allowMainThreadQueries()
            .build()

        sut = DiscoverShowsLocalDataSourceImpl(
            testDatabase.popularShowsDao(),
            testDatabase.topRatedShowsDao(),
            testDatabase.trendingShowsDao()
        )
    }

    @After
    fun cleanUp() {
        testDatabase.close()
    }

    // TODO: 05/01/2021 Find a way to test pagingSource

    @Test
    fun on_clearPopularShowsCache_database_should_be_empty() = testScope.runBlockingTest {

        val dao = testDatabase.popularShowsDao()
        dao.insertAll(mockPopularShows)
        assertThat("Database should not be empty", dao.popularShows().isNotEmpty())

        sut.clearPopularShowsCache()

        assertThat("Database should be empty", dao.popularShows().isEmpty())
    }

    @Test
    fun on_clearTrendingShowsCache_database_should_be_empty() = testScope.runBlockingTest {

        val dao = testDatabase.trendingShowsDao()
        dao.insertAll(mockTrendingShows)
        assertThat("Database should not be empty", dao.trendingShows().isNotEmpty())

        sut.clearTrendingShowsCache()

        assertThat("Database should be empty", dao.trendingShows().isEmpty())
    }

    @Test
    fun on_clearTopRatedShowsCache_database_should_be_empty() = testScope.runBlockingTest {

        val dao = testDatabase.topRatedShowsDao()
        dao.insertAll(mockTopRatedShows)
        assertThat("Database should not be empty", dao.topRatedShows().isNotEmpty())

        sut.clearTopRatedShowsCache()

        assertThat("Database should be empty", dao.topRatedShows().isEmpty())
    }

    @Test
    fun on_cacheTopRatedShowsCache_database_should_contains_correct_data() = testScope.runBlockingTest {

        sut.cacheTopRatedShows(mockTopRatedShows)

        val dataInDataBase = testDatabase.topRatedShowsDao().topRatedShows()

        assertThat("Database should not be empty", dataInDataBase.isNotEmpty())

    }

    @Test
    fun on_cacheTrendingShowsCache_database_should_contains_correct_data() = testScope.runBlockingTest {

        sut.cacheTrendingShows(mockTrendingShows)

        val dataInDataBase = testDatabase.trendingShowsDao().trendingShows()

        assertThat("Database should not be empty", dataInDataBase.isNotEmpty())

    }

    @Test
    fun on_cachePopularShowsCache_database_should_contains_correct_data() = testScope.runBlockingTest {

        sut.cachePopularShows(mockPopularShows)

        val dataInDataBase = testDatabase.popularShowsDao().popularShows()

        assertThat("Database should not be empty", dataInDataBase.isNotEmpty())

    }
}