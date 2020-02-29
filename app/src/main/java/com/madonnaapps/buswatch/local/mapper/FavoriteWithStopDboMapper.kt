package com.madonnaapps.buswatch.local.mapper

import com.madonnaapps.buswatch.domain.model.Location
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.local.model.FavoriteWithStopDbo
import javax.inject.Inject

class FavoriteWithStopDboMapper @Inject constructor() : CacheMapper<FavoriteWithStopDbo, Stop> {

    override fun mapFromCacheObject(cacheObject: FavoriteWithStopDbo): Stop {
        return Stop(
            cacheObject.stop.id,
            cacheObject.stop.code,
            cacheObject.stop.title,
            true,
            Location(cacheObject.stop.latitude, cacheObject.stop.longitude),
            cacheObject.stop.routes
        )
    }

    override fun mapToCacheObject(domainObject: Stop): FavoriteWithStopDbo {
        throw UnsupportedOperationException(
            "FavoriteWithStopDboMapper mapToCacheObject is an unsupported method"
        )
    }
}