package com.zk.trackshows.ui.details

import androidx.lifecycle.SavedStateHandle
import com.zk.trackshows.MainCoroutineRule
import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.repository.FakeWatchListRepository
import com.zk.trackshows.utils.detailScreenStateOnScreenLoad
import com.zk.trackshows.utils.initialDetailScreenState
import com.zk.trackshows.utils.mockShow
import kotlinx.coroutines.Dispatchers
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

class DetailViewModelTest {
    private lateinit var coroutineDispatcher: TestCoroutineDispatcher
    private lateinit var watchListRepository: FakeWatchListRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var sut: DetailViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        watchListRepository = FakeWatchListRepository()
        coroutineDispatcher = TestCoroutineDispatcher()
        savedStateHandle = SavedStateHandle()
        sut = DetailViewModel(watchListRepository, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        savedStateHandle.remove<Show>(SHOW_KEY)

    }

    @Test
    fun testDetailScreenInitialState() = mainCoroutineRule.testDispatcher.runBlockingTest {

        val detailScreenState = sut.detailScreenState.first()

        assertThat("Initial state should be empty", detailScreenState == initialDetailScreenState)
    }

    @Test
    fun testOnScreenLoadStateShouldHoldCorrectShow() = mainCoroutineRule.testDispatcher.runBlockingTest {

        //Given
        val showId = 1
        val data = mockShow(showId)
        sut.onEvent(Event.ScreenLoad(data))

        //Collect
        val detailScreenState = sut.detailScreenState.first()

        //Assert
        assertThat("On screen load state should hold show", detailScreenState == detailScreenStateOnScreenLoad(showId))
    }

    @Test
    fun testOnTapAddToShowListShowShouldBeStored() = mainCoroutineRule.testDispatcher.runBlockingTest {

        //Given
        val showId = 1
        val data = mockShow(showId)

        //On events
        sut.onEvent(Event.ScreenLoad(data))
        sut.onEvent(Event.TapAddToWatchList(data))

        //Collect
        val state: DetailScreenState = sut.detailScreenState.first()
        //Assert
        assertThat("State should indicate show is in watch list after tapping TapAddToWatchList(${data.name})", state.isInWatchList)
    }
}