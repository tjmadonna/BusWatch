package com.madonnaapps.buswatch.data.datastore.prediction

import com.madonnaapps.buswatch.domain.model.Prediction
import io.reactivex.Single

interface PredictionRemoteDataStore {

    fun getPredictionsForStopCode(stopCode: String): Single<List<Prediction>>

}