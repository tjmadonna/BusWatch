package com.madonnaapps.buswatch.domain.repository

import com.madonnaapps.buswatch.domain.model.Prediction
import io.reactivex.Single

interface PredictionRepository {

    fun getPredictionsForStopId(stopId: String): Single<List<Prediction>>

}