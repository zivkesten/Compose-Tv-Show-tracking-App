package com.zk.trackshows.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowShowResult(
    @SerializedName("pages") val page: Int,
    @SerializedName("results") val shows: List<Show>,
    @SerializedName("total_pages")val total_pages: Int,
    @SerializedName("total_results")val total_results: Int
): Parcelable