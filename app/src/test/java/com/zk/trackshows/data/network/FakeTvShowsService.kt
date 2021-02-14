package com.zk.trackshows.data.network

import com.zk.trackshows.data.network.api.TvShowResponse
import com.zk.trackshows.data.network.api.TvShowsService
import com.zk.trackshows.utils.mockShowDto
import com.zk.trackshows.utils.mockShowsDto
import com.zk.trackshows.utils.randomDataId

class FakeTvShowsService: TvShowsService {
    override suspend fun fetchPagedPopularShows(page: Int): TvShowResponse {
        return TvShowResponse(1, mockShowsDto)
    }

    override suspend fun fetchPagedTopRatedShows(page: Int): TvShowResponse {
        return TvShowResponse(1, mockShowsDto)
    }

    override suspend fun fetchPagedTrendingTVShows(page: Int): TvShowResponse {
        return TvShowResponse(1, mockShowsDto)
    }
}