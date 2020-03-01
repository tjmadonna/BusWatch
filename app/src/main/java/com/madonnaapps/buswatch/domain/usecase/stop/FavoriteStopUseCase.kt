package com.madonnaapps.buswatch.domain.usecase.stop

import com.madonnaapps.buswatch.domain.executor.PostExecutionThread
import com.madonnaapps.buswatch.domain.repository.StopRepository
import com.madonnaapps.buswatch.domain.usecase.base.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class FavoriteStopUseCase @Inject constructor(
    private val stopRepository: StopRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<FavoriteStopUseCase.Params>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null)
            throw IllegalArgumentException("FavoriteStopUseCase Params cannot be null")
        return stopRepository.favoriteStop(params.stopId)
    }

    data class Params(val stopId: String)
}