package com.madonnaapps.buswatch.file.mapper

import com.madonnaapps.buswatch.domain.model.Location
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.file.model.DeserializedStop
import javax.inject.Inject

class DeserializedStopMapper @Inject constructor() : FileMapper<DeserializedStop, Stop?> {

    override fun mapFromFileObject(fileObject: DeserializedStop): Stop? {
        val routes = fileObject.routes ?: return null
        if (routes.isEmpty()) return null

        val id = fileObject.id ?: return null
        val code = fileObject.code ?: return null
        val title = fileObject.title ?: return null
        val latitude = fileObject.latitude ?: return null
        val longitude = fileObject.longitude ?: return null

        return Stop(
            id,
            code,
            title,
            false,
            Location(latitude, longitude),
            routes
        )
    }

}