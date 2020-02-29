package com.madonnaapps.buswatch.cache.datastore

import com.madonnaapps.buswatch.cache.dao.StopDao
import com.madonnaapps.buswatch.cache.dao.StopVersionDao
import com.madonnaapps.buswatch.cache.mapper.CacheMapper
import com.madonnaapps.buswatch.cache.model.StopDbo
import com.madonnaapps.buswatch.cache.model.StopVersionDbo
import com.madonnaapps.buswatch.cache.model.StopWithFavoriteDbo
import com.madonnaapps.buswatch.data.datastore.stop.StopLocalDataStore
import com.madonnaapps.buswatch.domain.model.LocationBounds
import com.madonnaapps.buswatch.domain.model.Stop
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class StopLocalDataStoreImpl @Inject constructor(
    private val stopDao: StopDao,
    private val stopVersionDao: StopVersionDao,
    private val stopDboMapper: CacheMapper<StopDbo, Stop>,
    private val stopWithFavoriteDboMapper: CacheMapper<StopWithFavoriteDbo, Stop>
) : StopLocalDataStore {

    override fun getStopsInLocationBounds(locationBounds: LocationBounds): Observable<List<Stop>> {
        return stopDao.getStopsInLocationBounds(
            locationBounds.north,
            locationBounds.south,
            locationBounds.west,
            locationBounds.east
        ).map { dboList -> dboList.map { stopWithFavoriteDboMapper.mapFromCacheObject(it) } }
    }

    override fun getStopById(id: String): Single<Stop> {
        return stopDao.getStopById(id)
            .map { dbo -> stopWithFavoriteDboMapper.mapFromCacheObject(dbo) }
    }

    override fun getStopVersion(): Single<Int> {
        return stopVersionDao.getStopVersion()
            .map { dbo -> dbo.version }
            .onErrorReturnItem(-1)
    }

    override fun refreshStops(stops: List<Stop>, version: Int): Completable {
        return Completable.defer {

            // Create a map of the new stops with id being the key
            val refreshStopMap = stops.map { stop -> stopDboMapper.mapToCacheObject(stop) }
                .associateBy { it.id }

            val upsertStops = mutableListOf<StopDbo>()
            val deleteStops = mutableListOf<StopDbo>()

            stopDao.getAllStops().forEach { stop ->
                refreshStopMap[stop.id]?.let { refreshStop ->
                    // The new list contains this stop...upsert it
                    upsertStops.add(refreshStop)
                } ?: run {
                    // The new list does not contain this stop...delete it
                    deleteStops.add(stop)
                }
            }

            stopDao.refreshStops(upsertStops, deleteStops)

            val versionDbo = StopVersionDbo(version = version)
            stopVersionDao.insertStopVersion(versionDbo)

            Completable.complete()
        }
    }
}