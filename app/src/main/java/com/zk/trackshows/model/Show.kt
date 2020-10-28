package com.zk.trackshows.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Show(
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
): Parcelable {
    fun mock(): Show {
       return Show(
            "/eTMMU2rKpZRbo9ERytyhwatwAjz",
            "",
            emptyList(),
            0,
            "the title",
            emptyList(),
            "english",
            "original",
            "overview",
            2.0,
            "/eTMMU2rKpZRbo9ERytyhwatwAjz",
            1.5,
            1
        )
    }
}