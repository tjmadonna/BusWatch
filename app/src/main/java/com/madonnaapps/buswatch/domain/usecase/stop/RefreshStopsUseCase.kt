package com.madonnaapps.buswatch.domain.usecase.stop

import com.madonnaapps.buswatch.domain.executor.PostExecutionThread
import com.madonnaapps.buswatch.domain.repository.StopRepository
import com.madonnaapps.buswatch.domain.usecase.base.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class RefreshStopsUseCase @Inject constructor(
    private val stopRepository: StopRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<Nothing?>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: Nothing?): Completable {
        return stopRepository.refreshStops()
    }
}