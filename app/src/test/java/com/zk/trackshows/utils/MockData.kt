package com.zk.trackshows.utils

import androidx.paging.PagingData
import com.zk.trackshows.data.local.mapper.ShowEntityMapper
import com.zk.trackshows.data.local.model.*
import com.zk.trackshows.data.network.model.ShowDto
import com.zk.trackshows.domain.model.Show
import com.zk.trackshows.ui.details.DetailScreenState
import com.zk.trackshows.ui.main.WatchListState
import kotlin.random.Random


private val entityMapper = ShowEntityMapper()

fun Show.isSameAsShow(show: Show?) = this.id == show?.id && this.name == show.name

fun DetailScreenState.isSameState(state: DetailScreenState): Boolean {
    return this.show?.isSameAsShow(state.show) == true && this.isInWatchList == state.isInWatchList
}

val initialWatchListState = WatchListState(loading = true)

val initialDetailScreenState = DetailScreenState()

val randomDataId = Random(100).nextInt()

val randomPageNumber = Random(10).nextInt()


fun mockShow(id: Int) =  Show(id, name = "Mock Show $id")

fun mockShowEntity(id: Int) =  entityMapper.mapFromDomainModel(mockShow(id))

fun mockWatchedShow(id: Int) = WatchedShow(entityMapper.mapFromDomainModel(mockShow(id)))

fun mockPopularShow(id: Int) = PopularShow(entityMapper.mapFromDomainModel(mockShow(id)))

fun mockTopRatedShow(id: Int) = TopRatedShow(entityMapper.mapFromDomainModel(mockShow(id)))

fun mockTrendingShow(id: Int) = TrendingShow(entityMapper.mapFromDomainModel(mockShow(id)))

fun mockShowDto(id: Int) = ShowDto(id, name = "Mock Show $id")


val mockShows = listOf(
    mockShow(0),
    mockShow(1),
    mockShow(2),
    mockShow(3),
    mockShow(4),
)

val watchListStateOnScreenLoad = WatchListState(loading = false, watchedShows = mockShows)

val mockWatchedShows = listOf(
    mockWatchedShow(1),
    mockWatchedShow(2),
    mockWatchedShow(3),
    mockWatchedShow(4),
    mockWatchedShow(5),
)

fun detailScreenStateOnScreenLoad(showId: Int) = DetailScreenState(
    show = mockShow(showId),
    isInWatchList = mockWatchedShows.contains(mockWatchedShow(showId)))

val mockPopularShows = listOf(
    mockPopularShow(1),
    mockPopularShow(2),
    mockPopularShow(3),
    mockPopularShow(4),
    mockPopularShow(5)
)

val mockTopRatedShows = listOf(
    mockTopRatedShow(1),
    mockTopRatedShow(2),
    mockTopRatedShow(3),
    mockTopRatedShow(4),
    mockTopRatedShow(5)
)

val mockTrendingShows = listOf(
    mockTrendingShow(1),
    mockTrendingShow(2),
    mockTrendingShow(3),
    mockTrendingShow(4),
    mockTrendingShow(5)
)

val mockShowsDto = listOf(
    mockShowDto(randomDataId),
    mockShowDto(randomDataId),
    mockShowDto(randomDataId),
    mockShowDto(randomDataId),
    mockShowDto(randomDataId),
    mockShowDto(randomDataId)
)

val mockPagingDataOfPopularShows = PagingData.from(mockPopularShows)
val mockPagingDataOfTopRatedShows = PagingData.from(mockTopRatedShows)
val mockPagingDataOfTrendingShows = PagingData.from(mockTrendingShows)
val mockPagingDataOfShows = PagingData.from(mockShows)
