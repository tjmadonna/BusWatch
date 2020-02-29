package com.madonnaapps.buswatch.data.datastore.stop

import com.madonnaapps.buswatch.domain.model.LocationBounds
import com.madonnaapps.buswatch.domain.model.Stop
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface StopLocalDataStore {

    fun getStopsInLocationBounds(locationBounds: LocationBounds): Observable<List<Stop>>

    fun getStopById(id: String): Single<Stop>

    fun getStopVersion(): Single<Int>

    fun refreshStops(stops: List<Stop>, version: Int): Completable

    fun getFavoriteStops(): Observable<List<Stop>>

    fun favoriteStop(stopId: String): Completable

    fun unfavoriteStop(stopId: String): Completable

}