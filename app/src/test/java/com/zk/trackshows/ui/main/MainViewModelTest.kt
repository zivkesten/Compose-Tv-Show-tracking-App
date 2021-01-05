package com.zk.trackshows.ui.main

import androidx.paging.*
import com.zk.trackshows.AppScreens
import com.zk.trackshows.MainCoroutineRule
import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.repository.FakeDiscoverShowsRepository
import com.zk.trackshows.repository.FakeWatchListRepository
import com.zk.trackshows.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModelTest {

    private lateinit var coroutineDispatcher: TestCoroutineDispatcher
    private lateinit var watchListRepository: FakeWatchListRepository
    private lateinit var discoverShowsListRepository: FakeDiscoverShowsRepository
    private lateinit var sut: MainViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        discoverShowsListRepository = FakeDiscoverShowsRepository()
        watchListRepository = FakeWatchListRepository()
        coroutineDispatcher = TestCoroutineDispatcher()
        sut = MainViewModel(watchListRepository, discoverShowsListRepository)
    }

    @After
    fun tearDown() {
        watchListRepository.watchedShowsStateFlow.value = listOf()
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialValueOfNavigationEventsStateFlow() = mainCoroutineRule.testDispatcher.runBlockingTest {
        //When
        val initialNavigationValue = sut.navigationEvent.first()
        //Assert that
        assertThat("initial screen value should be MainScreen",
            initialNavigationValue == AppScreens.MainScreen) //
    }

    @Test
    fun tapSearch_emitSearchNavigationEvent() = mainCoroutineRule.testDispatcher.runBlockingTest {
        //on Event
        sut.tapSearch()
        //Collect
        val navigationEventScreen = sut.navigationEvent.value
        //Assert
        assertThat("oo tap search value should be Search",
            navigationEventScreen == AppScreens.Search) //
    }

    @Test
    fun tapShow_emitShowDetailsNavigationEventWithCorrectShow() = mainCoroutineRule.testDispatcher.runBlockingTest {
        //on Event
        val show = mockShow(1)
        sut.tapShowEvent(show)
        //Collect
        val navigationEventScreen = sut.navigationEvent.value
        //Assert
        assertThat("on tap show value should be Details",
            navigationEventScreen == AppScreens.Details(show))
    }

    @Test
    fun initialState_emptyState() = mainCoroutineRule.testDispatcher.runBlockingTest {
        //Collect initial state
        val watchListState = sut.watchListState.value
        //Assert
        assertThat("Initial state should match", watchListState == initialWatchListState)
    }

    @Test
    fun onScreenLoad_eventStateShouldHaveShows() = mainCoroutineRule.testDispatcher.runBlockingTest {

        //Populate Watch list
        repeat(5) { count ->
            watchListRepository.addToWatchList(mockShow(count))
        }

        //on Event
        sut.onEvent(MainScreenEvent.ScreenLoad)

        //Collect
        val watchListState = sut.watchListState.first()

        //Assert
        assertThat("list", watchListState == watchListStateOnScreenLoad)
    }

    @Test
    fun testPopularShowsPagedDataflow() = mainCoroutineRule.testDispatcher.runBlockingTest {

        //Collect
        val popularShowsPagedData: PagingData<Show> = sut.popularShowsPagedData.first()

        //Assert
        assertThat("list", popularShowsPagedData == mockPagingDataOfShows)
    }

    @Test
    fun testTopRatedShowsPagedDataflow() = mainCoroutineRule.testDispatcher.runBlockingTest {

        //Collect
        val topRatedShowsPagedData: PagingData<Show> = sut.topRatedShowsPagedData.first()

        //Assert
        assertThat("list", topRatedShowsPagedData == mockPagingDataOfShows )
    }

    @Test
    fun testTrendingShowsPagedDataflow() = mainCoroutineRule.testDispatcher.runBlockingTest {

        //Collect
        val trendingShowsPagedData: PagingData<Show> = sut.trendingShowsPagedData.first()

        //Assert
        assertThat("list", trendingShowsPagedData == mockPagingDataOfShows)
    }
}