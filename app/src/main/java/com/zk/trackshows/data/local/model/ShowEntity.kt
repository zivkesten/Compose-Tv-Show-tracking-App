package com.zk.trackshows.data.local.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Immutable
@Parcelize
data class ShowEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "backdrop_path") val backdrop_path: String? = null,
    @ColumnInfo(name = "first_air_date") val first_air_date: String? = null,
    //@ColumnInfo(name = "genre_ids") val genre_ids: List<Int> = listOf(),
    @ColumnInfo(name = "name") val name: String? = null,
    //@ColumnInfo(name = "origin_country") val origin_country: List<String>,
    @ColumnInfo(name = "original_language") val original_language: String? = null,
    @ColumnInfo(name = "original_name") val original_name: String? = null,
    @ColumnInfo(name = "overview") val overview: String? = null,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_path") val poster_path: String? = null,
    @ColumnInfo(name = "vote_average") val vote_average: Double,
    @ColumnInfo(name = "vote_count") val vote_count: Int
) : Parcelable