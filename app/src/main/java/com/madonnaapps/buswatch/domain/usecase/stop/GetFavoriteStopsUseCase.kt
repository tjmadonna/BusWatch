package com.madonnaapps.buswatch.domain.usecase.stop

import com.madonnaapps.buswatch.domain.executor.PostExecutionThread
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.domain.repository.StopRepository
import com.madonnaapps.buswatch.domain.usecase.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class GetFavoriteStopsUseCase @Inject constructor(
    private val stopRepository: StopRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Stop>, Nothing?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Stop>> {
        return stopRepository.getFavoriteStops()
    }
}