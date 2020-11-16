package com.zk.trackshows.repository.network

import com.zk.trackshows.model.PopularShows
import retrofit2.Response
import retrofit2.http.GET


interface TvShowsService {

    @GET("popular")
    suspend fun fetchPopularShows(): Response<PopularShows>

}