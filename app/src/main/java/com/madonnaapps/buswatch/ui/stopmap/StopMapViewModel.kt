package com.madonnaapps.buswatch.ui.stopmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.madonnaapps.buswatch.domain.model.Location
import com.madonnaapps.buswatch.domain.model.LocationZoom
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.domain.usecase.location.GetLastLocationUseCase
import com.madonnaapps.buswatch.domain.usecase.location.SaveLastLocationUseCase
import com.madonnaapps.buswatch.domain.usecase.stop.GetStopsInLocationBoundsUseCase
import com.madonnaapps.buswatch.ui.stopmap.contract.StopMapIntent
import com.madonnaapps.buswatch.ui.stopmap.contract.StopMapIntent.*
import com.madonnaapps.buswatch.ui.stopmap.contract.StopMapState
import com.madonnaapps.buswatch.ui.stopmap.contract.StopMapState.*
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class StopMapViewModel @Inject constructor(
    private val getStopsInLocationBoundsUseCase: GetStopsInLocationBoundsUseCase,
    private val saveLastLocationUseCase: SaveLastLocationUseCase,
    private val getLastLocationUseCase: GetLastLocationUseCase
) : ViewModel() {

    companion object {
        private val DEFAULT_LOCATION = Location(40.44062, -79.99589)
        private const val DEFAULT_ZOOM = 14f
    }

    private val _state = MutableLiveData<StopMapState>()

    val state: LiveData<StopMapState>
        get() = _state

    init {
        getLastLocationUseCase.execute(GetLastLocationSubscriber())
    }

    override fun onCleared() {
        getLastLocationUseCase.dispose()
        getStopsInLocationBoundsUseCase.dispose()
        saveLastLocationUseCase.dispose()
        super.onCleared()
    }

    fun handleIntent(intent: StopMapIntent) {
        when (intent) {
            is MoveLocationStopMapIntent -> handleMoveLocationStopMapIntent(intent)
        }
    }

    private fun handleMoveLocationStopMapIntent(intent: MoveLocationStopMapIntent) {
        getStopsInLocationBoundsUseCase.clear()
        if (intent.locationZoom.zoom >= DEFAULT_ZOOM) {
            saveLastLocationUseCase.execute(
                SaveLastLocationSubscriber(),
                SaveLastLocationUseCase.Params(intent.locationZoom)
            )
            getStopsInLocationBoundsUseCase.execute(
                GetStopsSubscriber(intent.locationZoom),
                GetStopsInLocationBoundsUseCase.Params(intent.locationBounds)
            )
        } else {
            _state.value = SetLocationWithStopsStopMapState(intent.locationZoom, emptyList())
        }
    }

    // Disposable Observers

    private inner class GetStopsSubscriber(
        private val locationZoom: LocationZoom
    ) : DisposableObserver<List<Stop>>() {

        override fun onNext(t: List<Stop>) {
            _state.value = SetLocationWithStopsStopMapState(locationZoom, t)
        }

        override fun onError(e: Throwable) = Unit

        override fun onComplete() = Unit
    }

    private inner class GetLastLocationSubscriber : DisposableSingleObserver<LocationZoom>() {

        override fun onSuccess(t: LocationZoom) {
            _state.value = SetLocationStopMapState(t)
        }

        override fun onError(e: Throwable) {
            val default = LocationZoom(DEFAULT_LOCATION, DEFAULT_ZOOM)
            _state.value = SetLocationStopMapState(default)
        }
    }

    private inner class SaveLastLocationSubscriber : DisposableCompletableObserver() {

        override fun onComplete() = Unit

        override fun onError(e: Throwable) = Unit
    }
}