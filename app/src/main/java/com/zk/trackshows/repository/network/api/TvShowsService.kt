package com.zk.trackshows.repository.network.api

import com.zk.trackshows.repository.network.api.TvShowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface TvShowsService {

    @GET("tv/popular")
    suspend fun fetchPopularShows(): Response<TvShowResponse>

    @GET("tv/popular")
    suspend fun fetchPagedPopularShows(@Query("page") page: Int): TvShowResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(): Response<TvShowResponse>

    @GET("tv/top_rated")
    suspend fun fetchPagedTopRatedShows(@Query("page") page: Int): TvShowResponse

    @GET("trending/tv/week")
    suspend fun fetchPagedTrendingTVShows(@Query("page") page: Int): TvShowResponse

}