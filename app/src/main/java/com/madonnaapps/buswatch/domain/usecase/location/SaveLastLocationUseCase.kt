package com.madonnaapps.buswatch.domain.usecase.location

import com.madonnaapps.buswatch.domain.executor.PostExecutionThread
import com.madonnaapps.buswatch.domain.model.LocationZoom
import com.madonnaapps.buswatch.domain.repository.LocationRepository
import com.madonnaapps.buswatch.domain.usecase.base.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class SaveLastLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<SaveLastLocationUseCase.Params>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null)
            throw IllegalArgumentException("SaveLastLocationUseCase Params cannot be null")
        return locationRepository.saveLastLocation(params.locationZoom)
    }

    data class Params(val locationZoom: LocationZoom)
}