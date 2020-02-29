package com.madonnaapps.buswatch.domain.repository

import com.madonnaapps.buswatch.domain.model.LocationBounds
import com.madonnaapps.buswatch.domain.model.Stop
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface StopRepository {

    fun getStopsInLocationBounds(locationBounds: LocationBounds): Observable<List<Stop>>

    fun getStopById(id: String): Single<Stop>

    fun refreshStops(): Completable

}