package com.madonnaapps.buswatch.domain.repository

import com.madonnaapps.buswatch.domain.model.LocationZoom
import io.reactivex.Completable
import io.reactivex.Single

interface LocationRepository {

    fun getLastLocation(): Single<LocationZoom>

    fun saveLastLocation(locationZoom: LocationZoom): Completable

}