package com.zk.trackshows.data.network.model

import com.google.gson.annotations.SerializedName

data class ShowDto(
    @SerializedName("id") val id: Int,
    @SerializedName("backdrop_path") val backdrop_path: String? = null,
    @SerializedName("first_air_date") val first_air_date: String? = null,
    @SerializedName("genre_ids") val genre_ids: List<Int>? = listOf(),
    @SerializedName("name") val name: String? = null,
    @SerializedName("origin_country") val origin_country: List<String>? = listOf(),
    @SerializedName("original_language") val original_language: String? = null,
    @SerializedName("original_name") val original_name: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Double? = 0.0,
    @SerializedName("poster_path") val poster_path: String? = null,
    @SerializedName("vote_average") val vote_average: Double? = 0.0,
    @SerializedName("vote_count") val vote_count: Int? = 0
)
