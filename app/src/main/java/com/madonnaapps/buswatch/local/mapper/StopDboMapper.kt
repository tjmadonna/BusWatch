package com.madonnaapps.buswatch.local.mapper

import com.madonnaapps.buswatch.local.model.StopDbo
import com.madonnaapps.buswatch.domain.model.Location
import com.madonnaapps.buswatch.domain.model.Stop
import javax.inject.Inject

class StopDboMapper @Inject constructor() : CacheMapper<StopDbo, Stop> {

    override fun mapFromCacheObject(cacheObject: StopDbo): Stop {
        return Stop(
            cacheObject.id,
            cacheObject.code,
            cacheObject.title,
            false,
            Location(cacheObject.latitude, cacheObject.longitude),
            cacheObject.routes
        )
    }

    override fun mapToCacheObject(domainObject: Stop): StopDbo {
        return StopDbo(
            domainObject.id,
            domainObject.code,
            domainObject.title,
            domainObject.location.latitude,
            domainObject.location.longitude,
            domainObject.routes
        )
    }
}