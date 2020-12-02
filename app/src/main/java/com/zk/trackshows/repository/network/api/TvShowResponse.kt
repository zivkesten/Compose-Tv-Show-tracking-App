package com.zk.trackshows.repository.network.api

import com.google.gson.annotations.SerializedName
import com.zk.trackshows.model.Show

data class TvShowResponse(
    @SerializedName("pages") val pages: Int,
    @SerializedName("results") val shows: List<Show>
)