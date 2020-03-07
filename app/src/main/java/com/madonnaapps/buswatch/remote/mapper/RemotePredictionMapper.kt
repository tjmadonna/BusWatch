package com.madonnaapps.buswatch.remote.mapper

import com.madonnaapps.buswatch.domain.model.Prediction
import com.madonnaapps.buswatch.remote.model.prediction.RemotePrediction
import java.text.SimpleDateFormat
import javax.inject.Inject

class RemotePredictionMapper @Inject constructor(
    private val dateFormat: SimpleDateFormat
) : RemoteMapper<RemotePrediction, Prediction?> {

    override fun mapFromRemoteObject(remoteObject: RemotePrediction): Prediction? {
        val vehicleId = remoteObject.vehicleId ?: return null
        val arrivalTime = remoteObject.arrivalTime ?: return null
        val arrivalDate = dateFormat.parse(arrivalTime) ?: return null
        val route = remoteObject.route ?: return null
        val destination = remoteObject.destination ?: return null
        val direction = remoteObject.direction ?: return null

        return Prediction(vehicleId, arrivalDate, route, destination, direction)
    }
}