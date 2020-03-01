package com.madonnaapps.buswatch.local.datastore

import com.madonnaapps.buswatch.data.datastore.location.LocationLocalDataStore
import com.madonnaapps.buswatch.domain.model.LocationZoom
import com.madonnaapps.buswatch.local.dao.LastLocationDao
import com.madonnaapps.buswatch.local.mapper.CacheMapper
import com.madonnaapps.buswatch.local.model.LastLocationDbo
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LocationLocalDataStoreImpl @Inject constructor(
    private val lastLocationDao: LastLocationDao,
    private val lastLocationMapper: CacheMapper<LastLocationDbo, LocationZoom>
) : LocationLocalDataStore {

    override fun getLastLocation(): Single<LocationZoom> {
        return lastLocationDao.getLastLocation()
            .map { lastLocationMapper.mapFromCacheObject(it) }
    }

    override fun saveLastLocation(locationZoom: LocationZoom): Completable {
        return lastLocationDao.insertLastLocation(
            lastLocationDbo = lastLocationMapper.mapToCacheObject(locationZoom)
        )
    }

}