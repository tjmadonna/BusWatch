package com.madonnaapps.buswatch.domain.usecase.stop

import com.madonnaapps.buswatch.domain.executor.PostExecutionThread
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.domain.repository.StopRepository
import com.madonnaapps.buswatch.domain.usecase.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class GetStopByIdUseCase @Inject constructor(
    private val stopRepository: StopRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<Stop, GetStopByIdUseCase.Params>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<Stop> {
        if (params == null)
            throw IllegalArgumentException("GetStopById Params cannot be null")
        return stopRepository.getStopById(params.stopId)
    }

    data class Params(val stopId: String)
}