package com.zk.trackshows.data.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface TvShowsService {

    @GET("tv/popular")
    suspend fun fetchPagedPopularShows(@Query("page") page: Int): TvShowResponse

    @GET("tv/top_rated")
    suspend fun fetchPagedTopRatedShows(@Query("page") page: Int): TvShowResponse

    @GET("trending/tv/week")
    suspend fun fetchPagedTrendingTVShows(@Query("page") page: Int): TvShowResponse

}