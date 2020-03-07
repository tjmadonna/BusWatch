package com.madonnaapps.buswatch.domain.repository

import com.madonnaapps.buswatch.domain.model.LocationBounds
import com.madonnaapps.buswatch.domain.model.Stop
import io.reactivex.Completable
import io.reactivex.Observable

interface StopRepository {

    fun getStopsInLocationBounds(locationBounds: LocationBounds): Observable<List<Stop>>

    fun getStopById(id: String): Observable<Stop>

    fun refreshStops(): Completable

    fun getFavoriteStops(): Observable<List<Stop>>

    fun favoriteStop(stopId: String): Completable

    fun unfavoriteStop(stopId: String): Completable

}