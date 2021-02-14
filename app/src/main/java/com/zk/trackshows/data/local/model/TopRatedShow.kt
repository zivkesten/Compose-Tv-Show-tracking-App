package com.zk.trackshows.data.local.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class TopRatedShow(
    @Embedded
    val show: ShowEntity,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "topRatedShowId") val id: Int = show.id
) : Parcelable, DataBaseShow {

    override fun entity(): ShowEntity {
        return show
    }
}

