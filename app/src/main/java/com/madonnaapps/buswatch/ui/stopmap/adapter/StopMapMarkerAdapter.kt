package com.madonnaapps.buswatch.ui.stopmap.adapter

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.ui.common.extension.MarkerOptions

class StopMapMarkerAdapter(private val googleMap: GoogleMap) {

    private val markers = HashMap<String, Marker>()

    fun submitStops(stops: List<Stop>) {
        if (stops.isEmpty()) {
            googleMap.clear()
            markers.clear()
            return
        }

        val markersToDelete = markers.keys

        stops.forEach { stop ->
            if (markers.containsKey(stop.id)) {
                // Marker already exists on map, delete from markersToDelete
                markersToDelete.remove(stop.id)
            } else {
                // Marker doesn't exist, create one and add to markers
                val markerOptions = MarkerOptions(stop.location, stop.title)
                    .snippet(stop.routes.joinToString(separator = ", "))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                val marker = googleMap.addMarker(markerOptions)
                marker.tag = stop.id
                markers[stop.id] = marker
            }
        }

        // Markers still left in markersToDelete should not be in the new state, delete them
        markersToDelete.forEach { stopId ->
            val marker = markers.remove(stopId)
            marker?.remove() // Remove marker from google map
        }
    }
}