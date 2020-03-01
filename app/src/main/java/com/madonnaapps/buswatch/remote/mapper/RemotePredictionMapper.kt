package com.madonnaapps.buswatch.remote.mapper

import com.madonnaapps.buswatch.domain.model.Prediction
import com.madonnaapps.buswatch.remote.model.prediction.RemotePrediction
import java.util.*
import javax.inject.Inject

class RemotePredictionMapper @Inject constructor() : RemoteMapper<RemotePrediction, Prediction?> {

    override fun mapFromRemoteObject(remoteObject: RemotePrediction): Prediction? {
        val arrivalTime = remoteObject.arrivalTime ?: return null
        val route = remoteObject.route ?: return null
        val destination = remoteObject.destination ?: return null

        return Prediction(Date(arrivalTime), route, destination)
    }
}