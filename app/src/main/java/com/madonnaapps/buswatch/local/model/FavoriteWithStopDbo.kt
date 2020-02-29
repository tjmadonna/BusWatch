package com.madonnaapps.buswatch.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class FavoriteWithStopDbo(

    @Embedded
    val favoriteStop: FavoriteStopDbo,

    @Relation(
        parentColumn = "stop_id",
        entity = StopDbo::class,
        entityColumn = "id"
    )
    val stop: StopDbo

)