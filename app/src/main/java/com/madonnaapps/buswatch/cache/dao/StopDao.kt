package com.madonnaapps.buswatch.cache.dao

import androidx.room.*
import com.madonnaapps.buswatch.cache.model.StopDbo
import com.madonnaapps.buswatch.cache.model.StopWithFavoriteDbo
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class StopDao {

    @Query(
        "SELECT * FROM stops WHERE " +
                "latitude <= :northBound AND latitude >= :southBound AND " +
                "longitude >= :westBound AND longitude <= :eastBound"
    )
    abstract fun getStopsInLocationBounds(
        northBound: Double,
        southBound: Double,
        westBound: Double,
        eastBound: Double
    ): Observable<List<StopWithFavoriteDbo>>

    @Query("SELECT * FROM stops WHERE stops.id = :id LIMIT 1")
    abstract fun getStopById(id: String): Single<StopWithFavoriteDbo>

    @Query("SELECT * FROM stops")
    abstract fun getAllStops(): List<StopDbo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertStops(stops: List<StopDbo>): List<Long>

    @Update
    abstract fun updateStops(stops: List<StopDbo>)

    @Delete
    abstract fun deleteStops(stops: List<StopDbo>)

    @Transaction
    open fun refreshStops(stopsToUpsert: List<StopDbo>, stopsToDelete: List<StopDbo>) {

        if (stopsToUpsert.isNotEmpty()) {
            // This is basically upserting (updating/inserting) to maintain db relationships
            val insertResult = insertStops(stopsToUpsert)
            val stopsToUpdate = insertResult.mapIndexedNotNull { index: Int, result: Long ->
                // If result == -1, then it means stop exists already and must be updated
                return@mapIndexedNotNull if (result == -1L) stopsToUpsert[index] else null
            }

            if (stopsToUpdate.isNotEmpty()) {
                updateStops(stopsToUpdate)
            }
        }

        if (stopsToDelete.isNotEmpty()) {
            deleteStops(stopsToDelete)
        }
    }
}