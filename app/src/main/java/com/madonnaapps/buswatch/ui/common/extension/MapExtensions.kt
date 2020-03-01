package com.madonnaapps.buswatch.ui.common.extension

import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.madonnaapps.buswatch.domain.model.Location
import com.madonnaapps.buswatch.domain.model.LocationZoom

// Google Map Extensions

val GoogleMap.atNullPoint: Boolean
    get() = latitude.toInt() == 0 && longitude.toInt() == 0

val GoogleMap.latitude: Double
    get() = cameraPosition.target.latitude

val GoogleMap.longitude: Double
    get() = cameraPosition.target.longitude

val GoogleMap.zoom: Float
    get() = cameraPosition.zoom

val GoogleMap.northBound: Double
    get() = projection.visibleRegion.latLngBounds.northeast.latitude

val GoogleMap.southBound: Double
    get() = projection.visibleRegion.latLngBounds.southwest.latitude

val GoogleMap.westBound: Double
    get() = projection.visibleRegion.latLngBounds.southwest.longitude

val GoogleMap.eastBound: Double
    get() = projection.visibleRegion.latLngBounds.northeast.longitude

// MarkerOptions Extensions

fun MarkerOptions(location: Location, title: String): MarkerOptions = MarkerOptions().apply {
    this.position(LatLng(location.latitude, location.longitude))
    this.title(title)
}

object KotlinCameraUpdateFactory {

    fun newLatLngZoom(locationZoom: LocationZoom): CameraUpdate {
        return CameraUpdateFactory.newLatLngZoom(
            LatLng(
                locationZoom.location.latitude,
                locationZoom.location.longitude
            ),
            locationZoom.zoom
        )
    }
}