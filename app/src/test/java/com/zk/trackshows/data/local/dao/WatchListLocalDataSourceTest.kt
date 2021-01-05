package com.zk.trackshows.data.local.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.zk.trackshows.data.WatchListLocalDataSource
import com.zk.trackshows.data.local.ShowsDatabase
import com.zk.trackshows.data.local.WatchListLocalDataSourceImpl
import com.zk.trackshows.data.local.mapper.MockEntityMapper
import com.zk.trackshows.data.local.model.ShowEntity
import com.zk.trackshows.domain.mapper.DomainMapper
import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.utils.mockShow
import com.zk.trackshows.utils.mockWatchedShow
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.first
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
class WatchListLocalDataSourceTest {

    private lateinit var sut: WatchListLocalDataSourceImpl
    private lateinit var testDatabase: ShowsDatabase
    private lateinit var mapperMock: DomainMapper<ShowEntity, Show>

    private lateinit var testDispatcher: TestCoroutineDispatcher
    private lateinit var testScope: TestCoroutineScope

    @Before
    fun setup() {
        mapperMock = MockEntityMapper()
        testDispatcher = TestCoroutineDispatcher()
        testScope = TestCoroutineScope(testDispatcher)
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

        sut = WatchListLocalDataSourceImpl(testDatabase.watchListDao(), mapperMock)
    }

    @After
    fun cleanUp() {
        testDatabase.close()
    }

    @Test
    fun observeWatchedShows_should_return_flowOfDomainModel()  = testScope.runBlockingTest{

        //Given that the database contains this data
        val show1 = mockWatchedShow(1)
        val show2 = mockWatchedShow(2)
        val show3 = mockWatchedShow(3)
        val data = listOf(show1, show2, show3)
        testDatabase.watchListDao().insertAll(data)

        // The expected output should be
        val expectedValue = data.map { mapperMock.mapToDomainModel(it.show) }

        //Observe
        val watchedShows: List<Show> = sut.observeWatchedShows().first()

        //Assert
        assertThat("flow emission should contain a list of size ${expectedValue.size}",
            watchedShows.size == expectedValue.size)
        assertThat("flow emission should be $expectedValue",
            watchedShows == expectedValue)
    }

    @Test
    fun getWatchedShows_should_return_flowOfDomainModel()  = testScope.runBlockingTest{

        //Given that the database contains this data
        val show1 = mockWatchedShow(1)
        val show2 = mockWatchedShow(2)
        val show3 = mockWatchedShow(3)
        val data = listOf(show1, show2, show3)
        testDatabase.watchListDao().insertAll(data)

        // The expected output should be
        val expectedValue = data.map { mapperMock.mapToDomainModel(it.show) }

        //Observe
        val watchedShows: List<Show> = sut.getWatchList()

        //Assert
        assertThat("flow emission should contain a list of size ${expectedValue.size}",
            watchedShows.size == expectedValue.size)
        assertThat("flow emission should be $expectedValue",
            watchedShows == expectedValue)
    }

    @Test
    fun onAddToWatchList_dataSource_should_return_listOfDomainModel() = testScope.runBlockingTest {
        //Given
        val show1 = mockShow(1)
        val show2 = mockShow(2)
        val show3 = mockShow(3)
        val expectedValue = listOf(show1, show2, show3)

        //Event
        sut.addToWatchList(show1)
        sut.addToWatchList(show2)
        sut.addToWatchList(show3)

        val watchedShows = sut.getWatchList()

        //Assert
        assertThat("flow emission should contain a list of size ${expectedValue.size}",
            watchedShows.size == expectedValue.size)
        assertThat("flow emission should be $expectedValue",
            watchedShows == expectedValue)
    }

    @Test
    fun onRemoveFromWatchList_dataSource_should_return_correct_listOfDomainModel() = testScope.runBlockingTest {
        //Given that the database contains this data
        val show1 = mockWatchedShow(1)
        val show2 = mockWatchedShow(2)
        val show3 = mockWatchedShow(3)
        val data = mutableListOf(show1, show2, show3)
        testDatabase.watchListDao().insertAll(data)

        // The expected output should be
        val expectedValue = data.map { mapperMock.mapToDomainModel(it.show) }.toMutableList()
        //Remove last entry
        sut.removeFromWatchList(show3.id)
        //Update expectedValue
        expectedValue.removeIf { it.id == show3.id }

        val watchedShows = sut.getWatchList()

        //Assert
        assertThat("flow emission should contain a list of size ${expectedValue.size}",
            watchedShows.size == expectedValue.size)
        assertThat("flow emission should be $expectedValue",
            watchedShows == expectedValue)
    }
}