package com.madonnaapps.buswatch.domain.usecase.location

import com.madonnaapps.buswatch.domain.executor.PostExecutionThread
import com.madonnaapps.buswatch.domain.model.LocationZoom
import com.madonnaapps.buswatch.domain.repository.LocationRepository
import com.madonnaapps.buswatch.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetLastLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<LocationZoom, Nothing?>(postExecutionThread) {

    override fun buildUseCaseSingle(params: Nothing?): Single<LocationZoom> {
        return locationRepository.getLastLocation()
    }
}