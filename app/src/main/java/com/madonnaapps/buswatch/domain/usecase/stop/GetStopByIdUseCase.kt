package com.madonnaapps.buswatch.domain.usecase.stop

import com.madonnaapps.buswatch.domain.executor.PostExecutionThread
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.domain.repository.StopRepository
import com.madonnaapps.buswatch.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetStopByIdUseCase @Inject constructor(
    private val stopRepository: StopRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Stop, GetStopByIdUseCase.Params>(postExecutionThread) {

    override fun buildUseCaseSingle(params: Params?): Single<Stop> {
        if (params == null)
            throw IllegalArgumentException("GetStopById Params cannot be null")
        return stopRepository.getStopById(params.stopId)
    }

    data class Params(val stopId: String)
}