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
data class WatchedShow(
    @PrimaryKey(autoGenerate = true)
    val databaseId: Int? = null,
    @ColumnInfo(name = "id") val showId: Int,

    ) : Parcelable {
    companion object {
        fun mock(): WatchedShow {
            return WatchedShow(Math.random().toInt(), 0)
        }
    }
}

