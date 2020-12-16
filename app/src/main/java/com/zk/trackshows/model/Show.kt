package com.zk.trackshows.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Immutable
@Parcelize
data class Show(
  @PrimaryKey(autoGenerate = true)
  val databaseId: Int,
  @ColumnInfo(name = "id") val id: Int,
  @ColumnInfo(name = "backdrop_path") val backdrop_path: String? = null,
  @ColumnInfo(name = "first_air_date") val first_air_date: String,
  //@ColumnInfo(name = "genre_ids") val genre_ids: List<Int>,
  @ColumnInfo(name = "name") val name: String,
  // @ColumnInfo(name = "origin_country") val origin_country: List<String>,
  @ColumnInfo(name = "original_language") val original_language: String,
  @ColumnInfo(name = "original_name") val original_name: String,
  @ColumnInfo(name = "overview") val overview: String,
  @ColumnInfo(name = "popularity") val popularity: Double,
  @ColumnInfo(name = "poster_path") val poster_path: String,
  @ColumnInfo(name = "vote_average") val vote_average: Double,
  @ColumnInfo(name = "vote_count") val vote_count: Int
) : Parcelable {
  companion object {
    fun mock(): Show {
      return Show(Math.random().toInt(),
        0,
        "\"https://image.tmdb.org/t/p/w500/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg",
        "",
        "mock Show",
        "",
        "",
        "",
        0.0,
        "",
        0.0,
        0)
    }
  }
}

