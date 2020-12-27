package com.zk.trackshows.repository

import androidx.paging.PagingSource
import com.zk.trackshows.repository.network.api.TvShowResponse
import com.zk.trackshows.repository.network.model.ShowDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.http.Query

@ExperimentalCoroutinesApi
interface RemoteDataSource {

    fun observePagedShows(): PagingSource<Int, ShowDto>

    suspend fun fetchPagedPopularShows(@Query("page") page: Int): TvShowResponse

}