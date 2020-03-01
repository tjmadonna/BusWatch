package com.madonnaapps.buswatch.local.mapper

import com.madonnaapps.buswatch.domain.model.Location
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.local.model.StopWithFavoriteDbo
import javax.inject.Inject

class StopWithFavoriteDboMapper @Inject constructor() : CacheMapper<StopWithFavoriteDbo, Stop> {

    override fun mapFromCacheObject(cacheObject: StopWithFavoriteDbo): Stop {
        return Stop(
            cacheObject.stop.id,
            cacheObject.stop.code,
            cacheObject.stop.title,
            cacheObject.favoriteStop != null,
            Location(cacheObject.stop.latitude, cacheObject.stop.longitude),
            cacheObject.stop.routes
        )
    }

    override fun mapToCacheObject(domainObject: Stop): StopWithFavoriteDbo {
        throw UnsupportedOperationException(
            "StopWithFavoriteDboMapper mapToCacheObject is an unsupported method"
        )
    }

}