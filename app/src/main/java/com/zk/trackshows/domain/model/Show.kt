package com.zk.trackshows.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
The Domain model of show
 */
@Parcelize
data class Show(
  val id: Int,
  val backdrop_path: String? = null,
  val first_air_date: String? = null,
  val genre_ids: List<Int>? = listOf(),
  val name: String? = null,
  val origin_country: List<String>? = listOf(),
  val original_language: String? = null,
  val original_name: String? = null,
  val overview: String? = null,
  val popularity: Double? = 0.0,
  val poster_path: String? = null,
  val vote_average: Double? = 0.0,
  val vote_count: Int? = 0
): Parcelable

