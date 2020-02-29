package com.madonnaapps.buswatch.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_stops",
    foreignKeys = [
        ForeignKey(
            entity = StopDbo::class,
            parentColumns = ["id"],
            childColumns = ["stop_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class FavoriteStopDbo(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "stop_id")
    val stopId: String,

    @ColumnInfo(name = "user_title")
    val userTitle: String?
)