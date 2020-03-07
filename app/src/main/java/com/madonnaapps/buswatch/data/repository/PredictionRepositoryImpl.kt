package com.madonnaapps.buswatch.data.repository

import com.madonnaapps.buswatch.data.datastore.prediction.PredictionRemoteDataStore
import com.madonnaapps.buswatch.data.datastore.stop.StopLocalDataStore
import com.madonnaapps.buswatch.domain.model.Prediction
import com.madonnaapps.buswatch.domain.repository.PredictionRepository
import io.reactivex.Single
import javax.inject.Inject

class PredictionRepositoryImpl @Inject constructor(
    private val predictionRemoteDataStore: PredictionRemoteDataStore,
    private val stopLocalDataStore: StopLocalDataStore
) : PredictionRepository {

    override fun getPredictionsForStopId(stopId: String): Single<List<Prediction>> {
        return stopLocalDataStore.getStopById(stopId)
            .firstOrError()
            .flatMap { stop -> predictionRemoteDataStore.getPredictionsForStopCode(stop.code) }
    }
}