package com.madonnaapps.buswatch.remote.datastore

import com.madonnaapps.buswatch.data.datastore.prediction.PredictionRemoteDataStore
import com.madonnaapps.buswatch.domain.model.Prediction
import com.madonnaapps.buswatch.remote.exception.TrueTimeApiServiceException
import com.madonnaapps.buswatch.remote.mapper.RemoteMapper
import com.madonnaapps.buswatch.remote.model.prediction.RemotePrediction
import com.madonnaapps.buswatch.remote.model.prediction.RemotePredictionResponse
import com.madonnaapps.buswatch.remote.service.TrueTimeApiService
import io.reactivex.Single
import javax.inject.Inject

class PredictionRemoteDataStoreImpl @Inject constructor(
    private val trueTimeApiPrediction: TrueTimeApiService,
    private val predictionMapper: RemoteMapper<RemotePrediction, Prediction?>
) : PredictionRemoteDataStore {

    override fun getPredictionsForStopCode(stopCode: String): Single<List<Prediction>> {
        return trueTimeApiPrediction.getPredictions(stopCode)
            .map { response -> getPredictionsFromResponse(response) }
            .map { remotePredictions ->
                remotePredictions.mapNotNull { remotePrediction ->
                    predictionMapper.mapFromRemoteObject(remotePrediction)
                }
            }
    }

    @Throws(IllegalStateException::class, TrueTimeApiServiceException::class)
    private fun getPredictionsFromResponse(response: RemotePredictionResponse): List<RemotePrediction> {
        val result = response.result
            ?: throw IllegalStateException("PredictionRemoteDataStoreImpl did not return a valid result")

        if (result.error != null && result.error.isNotEmpty()) {
            val message = result.error.first().message
                ?: "TrueTimeApiService threw an error with no error message"
            throw TrueTimeApiServiceException(message)
        }

        return result.predictions?.let {
            it
        } ?: throw IllegalStateException("TrueTimeApiService did not return predictions or error")
    }
}