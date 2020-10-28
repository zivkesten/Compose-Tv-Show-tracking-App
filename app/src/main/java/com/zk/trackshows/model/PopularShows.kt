package com.zk.trackshows.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PopularShows(
    val page: Int,
    @SerializedName("results")
    val shows: List<Show>,
    val total_pages: Int,
    val total_results: Int
): Parcelable