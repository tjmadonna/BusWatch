package com.madonnaapps.buswatch.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.madonnaapps.buswatch.local.model.FavoriteWithStopDbo
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface FavoriteStopDao {

    @Query("SELECT * FROM favorite_stops")
    fun getFavoriteStops(): Observable<List<FavoriteWithStopDbo>>

    @Query("INSERT INTO favorite_stops (stop_id, user_title) VALUES (:stopId, :userTitle)")
    fun favoriteStop(stopId: String, userTitle: String?): Completable

    @Query("DELETE FROM favorite_stops WHERE stop_id = :stopId")
    fun unfavoriteStop(stopId: String): Completable

}