package com.zk.trackshows.data.network

import com.zk.trackshows.data.network.api.TvShowsService
import com.zk.trackshows.utils.randomPageNumber
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class RemoteDataSourceImplTest {

    private lateinit var sut: RemoteDataSourceImpl
    private lateinit var service: TvShowsService

    private lateinit var testDispatcher: TestCoroutineDispatcher
    private lateinit var testScope: TestCoroutineScope

    @Before
    fun setup() {
        testDispatcher = TestCoroutineDispatcher()
        testScope = TestCoroutineScope(TestCoroutineDispatcher())

        service = FakeTvShowsService()
        sut = RemoteDataSourceImpl(service)

    }

    @Test
    fun on_fetchPagedPopularShows_should_return_correct_data() = testScope.runBlockingTest {

        //Given
        val page = randomPageNumber

        //Expected
        val expected = service.fetchPagedPopularShows(page)

        //Actual
        val actual = sut.fetchPagedPopularShows(page)

        //Assert
        assertThat("expected result $expected should match actual result $actual", expected == actual)
    }

    @Test
    fun on_fetchPagedTopRatedShows_should_return_correct_data() = testScope.runBlockingTest {
        //Given
        val page = randomPageNumber

        //Expected
        val expected = service.fetchPagedTopRatedShows(page)

        //Actual
        val actual = sut.fetchPagedTopRatedShows(page)

        //Assert
        assertThat("expected result $expected should match actual result $actual", expected == actual)

    }

    @Test
    fun on_fetchPagedTrendingShows_should_return_correct_data() = testScope.runBlockingTest {
        //Given
        val page = randomPageNumber

        //Expected
        val expected = service.fetchPagedTrendingTVShows(page)

        //Actual
        val actual = sut.fetchPagedTrendingTVShows(page)

        //Assert
        assertThat("expected result $expected should match actual result $actual", expected == actual)

    }
}