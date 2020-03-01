package com.madonnaapps.buswatch.domain.usecase.prediction

import com.madonnaapps.buswatch.domain.executor.PostExecutionThread
import com.madonnaapps.buswatch.domain.model.Prediction
import com.madonnaapps.buswatch.domain.repository.PredictionRepository
import com.madonnaapps.buswatch.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetPredictionsForStopIdUseCase @Inject constructor(
    private val predictionRepository: PredictionRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Prediction>, GetPredictionsForStopIdUseCase.Params>(postExecutionThread) {

    override fun buildUseCaseSingle(params: Params?): Single<List<Prediction>> {
        if (params == null)
            throw IllegalArgumentException("GetPredictionsForStopIdUseCase Params cannot be null")
        return predictionRepository.getPredictionsForStopId(params.stopId)
    }

    data class Params(val stopId: String)
}