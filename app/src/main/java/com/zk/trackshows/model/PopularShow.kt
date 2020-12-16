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
data class PopularShow(
    @PrimaryKey(autoGenerate = true)
    val databaseId: Int,
    @ColumnInfo(name = "id") val showId: Int,

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

