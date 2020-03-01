package com.madonnaapps.buswatch.local.mapper

import com.madonnaapps.buswatch.domain.model.Location
import com.madonnaapps.buswatch.domain.model.LocationZoom
import com.madonnaapps.buswatch.local.model.LastLocationDbo
import javax.inject.Inject

class LastLocationDboMapper @Inject constructor() : CacheMapper<LastLocationDbo, LocationZoom> {

    override fun mapFromCacheObject(cacheObject: LastLocationDbo): LocationZoom {
        return LocationZoom(
            Location(cacheObject.latitude, cacheObject.longitude),
            cacheObject.zoom
        )
    }

    override fun mapToCacheObject(domainObject: LocationZoom): LastLocationDbo {
        return LastLocationDbo(
            latitude = domainObject.location.latitude,
            longitude = domainObject.location.longitude,
            zoom = domainObject.zoom
        )
    }

}