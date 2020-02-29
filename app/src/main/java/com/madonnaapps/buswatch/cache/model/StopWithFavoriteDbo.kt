package com.madonnaapps.buswatch.cache.model

import androidx.room.Embedded
import androidx.room.Relation

data class StopWithFavoriteDbo(

    @Embedded
    val stop: StopDbo,

    @Relation(
        parentColumn = "id",
        entity = FavoriteStopDbo::class,
        entityColumn = "stop_id"
    )
    val favoriteStop: FavoriteStopDbo?
)