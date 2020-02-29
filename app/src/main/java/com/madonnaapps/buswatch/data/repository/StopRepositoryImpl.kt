package com.madonnaapps.buswatch.data.repository

import com.madonnaapps.buswatch.data.datastore.stop.StopFileDataStore
import com.madonnaapps.buswatch.data.datastore.stop.StopLocalDataStore
import com.madonnaapps.buswatch.data.version.StopFileVersion
import com.madonnaapps.buswatch.domain.model.LocationBounds
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.domain.repository.StopRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class StopRepositoryImpl @Inject constructor(
    private val stopLocalDataStore: StopLocalDataStore,
    private val stopFileDataStore: StopFileDataStore,
    private val stopFileVersion: StopFileVersion
) : StopRepository {

    override fun getStopsInLocationBounds(locationBounds: LocationBounds): Observable<List<Stop>> {
        return stopLocalDataStore.getStopsInLocationBounds(locationBounds)
    }

    override fun getStopById(id: String): Single<Stop> {
        return stopLocalDataStore.getStopById(id)
    }

    override fun refreshStops(): Completable {
        return stopLocalDataStore.getStopVersion()
            .map { stopDataStoreVersion -> stopFileVersion.value > stopDataStoreVersion }
            .flatMapCompletable { needsRefreshed ->
                when (needsRefreshed) {
                    true -> refreshStops(stopFileVersion.value)
                    false -> Completable.complete()
                }
            }
    }

    private fun refreshStops(newStopVersion: Int) = stopFileDataStore.getStops()
        .flatMapCompletable { newStops ->
            stopLocalDataStore.refreshStops(
                newStops,
                newStopVersion
            )
        }
}