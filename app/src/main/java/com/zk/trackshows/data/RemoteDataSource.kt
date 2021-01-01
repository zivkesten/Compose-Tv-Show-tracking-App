package com.zk.trackshows.data

import androidx.paging.PagingSource
import com.zk.trackshows.data.network.api.TvShowResponse
import com.zk.trackshows.data.network.model.ShowDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.http.Query

@ExperimentalCoroutinesApi
interface RemoteDataSource {

    suspend fun fetchPagedPopularShows(@Query("page") page: Int): TvShowResponse

    suspend fun fetchPagedTopRatedShows(@Query("page") page: Int): TvShowResponse

    suspend fun fetchPagedTrendingShows(@Query("page") page: Int): TvShowResponse

}