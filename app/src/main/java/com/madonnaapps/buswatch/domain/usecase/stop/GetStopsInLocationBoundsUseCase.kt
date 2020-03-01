package com.madonnaapps.buswatch.domain.usecase.stop

import com.madonnaapps.buswatch.domain.executor.PostExecutionThread
import com.madonnaapps.buswatch.domain.model.LocationBounds
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.domain.repository.StopRepository
import com.madonnaapps.buswatch.domain.usecase.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class GetStopsInLocationBoundsUseCase @Inject constructor(
    private val stopRepository: StopRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Stop>, GetStopsInLocationBoundsUseCase.Params>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<List<Stop>> {
        if (params == null)
            throw IllegalArgumentException("GetStopsInLocationBounds Params cannot be null")
        return stopRepository.getStopsInLocationBounds(params.locationBounds)
    }

    data class Params(val locationBounds: LocationBounds)
}