package com.madonnaapps.buswatch.ui.stopmap.contract

import com.madonnaapps.buswatch.domain.model.LocationBounds
import com.madonnaapps.buswatch.domain.model.LocationZoom
import com.madonnaapps.buswatch.domain.model.Stop

sealed class StopMapIntent {

    data class MoveLocationStopMapIntent(
        val locationZoom: LocationZoom,
        val locationBounds: LocationBounds
    ) : StopMapIntent()

}

sealed class StopMapState {

    data class SetLocationStopMapState(val locationZoom: LocationZoom) : StopMapState()

    data class SetLocationWithStopsStopMapState(
        val locationZoom: LocationZoom,
        val stops: List<Stop>
    ) : StopMapState()
}