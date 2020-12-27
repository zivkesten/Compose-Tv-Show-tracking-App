package com.zk.trackshows.repository.network.api

import com.google.gson.annotations.SerializedName
import com.zk.trackshows.repository.network.model.ShowDto

data class TvShowResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val shows: List<ShowDto>
)