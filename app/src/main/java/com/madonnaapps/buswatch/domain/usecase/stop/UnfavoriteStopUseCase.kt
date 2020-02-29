package com.madonnaapps.buswatch.domain.usecase.stop

import com.madonnaapps.buswatch.domain.executor.PostExecutionThread
import com.madonnaapps.buswatch.domain.repository.StopRepository
import com.madonnaapps.buswatch.domain.usecase.base.CompletableUseCase
import io.reactivex.Completable
import java.lang.IllegalArgumentException
import javax.inject.Inject

class UnfavoriteStopUseCase @Inject constructor(
    private val stopRepository: StopRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<UnfavoriteStopUseCase.Params>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null)
            throw IllegalArgumentException("UnfavoriteStopUseCase Params cannot be null")
        return stopRepository.unfavoriteStop(params.stopId)
    }

    data class Params(val stopId: String)
}