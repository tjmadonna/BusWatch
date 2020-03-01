package com.madonnaapps.buswatch.ui.stopmap

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.madonnaapps.buswatch.domain.model.Location
import com.madonnaapps.buswatch.domain.model.LocationBounds
import com.madonnaapps.buswatch.domain.model.LocationZoom
import com.madonnaapps.buswatch.ui.common.extension.*
import com.madonnaapps.buswatch.ui.stopmap.adapter.StopInfoWindowAdapter
import com.madonnaapps.buswatch.ui.stopmap.contract.StopMapState.*
import com.madonnaapps.buswatch.ui.stopmap.contract.StopMapIntent.*
import javax.inject.Inject

class StopMapFragment : SupportMapFragment(), OnMapReadyCallback {

    companion object {
        fun newInstance(): StopMapFragment = StopMapFragment()
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)
            .get(StopMapViewModel::class.java)
    }

    private var googleMap: GoogleMap? = null

    private val markers = HashMap<String, Marker>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMapAsync(this)
    }

    // OnMapReadyCallback

    override fun onMapReady(map: GoogleMap?) {
        setupMapView(map)
        setupObservers()
    }

    // Setup Functions

    private fun setupMapView(map: GoogleMap?) {
        googleMap = map
        map?.isIndoorEnabled = false
        map?.isBuildingsEnabled = false
        map?.isTrafficEnabled = false
        map?.uiSettings?.isCompassEnabled = false
        map?.uiSettings?.isMyLocationButtonEnabled = false
        map?.uiSettings?.isMapToolbarEnabled = false
        map?.uiSettings?.isZoomControlsEnabled = false

        map?.setInfoWindowAdapter(StopInfoWindowAdapter(requireContext()))

        map?.setOnCameraIdleListener {
            googleMap?.let { map ->
                if (map.atNullPoint) {
                    return@setOnCameraIdleListener
                }

                val locationZoom = LocationZoom(
                    Location(map.latitude, map.longitude),
                    map.cameraPosition.zoom
                )

                val locationBounds = LocationBounds(
                    map.northBound,
                    map.southBound,
                    map.westBound,
                    map.eastBound
                )

                val intent = MoveLocationStopMapIntent(locationZoom, locationBounds)
                viewModel.handleIntent(intent)
            }
        }

        map?.setOnInfoWindowClickListener { marker ->
            Log.d("StopMapFragment", "=====${marker.tag as String}")
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is SetLocationStopMapState -> renderSetLocationStopMapState(state)
                is SetLocationWithStopsStopMapState -> renderSetLocationWithStopsStopMapState(state)
            }
        })
    }

    // Intent Functions

    private fun renderSetLocationStopMapState(state: SetLocationStopMapState) {
        googleMap?.let { map ->
            if (state.locationZoom.location.latitude != map.latitude ||
                state.locationZoom.location.longitude != map.longitude ||
                state.locationZoom.zoom != map.zoom
            ) {
                // Move the map if it's not set to new location
                val cameraPosition = KotlinCameraUpdateFactory.newLatLngZoom(state.locationZoom)
                map.moveCamera(cameraPosition)
            }
        }
    }

    private fun renderSetLocationWithStopsStopMapState(state: SetLocationWithStopsStopMapState) {
        googleMap?.let { map ->

            if (state.locationZoom.location.latitude != map.latitude ||
                state.locationZoom.location.longitude != map.longitude ||
                state.locationZoom.zoom != map.zoom
            ) {
                // Move the map if it's not set to new location
                val cameraPosition = KotlinCameraUpdateFactory.newLatLngZoom(state.locationZoom)
                map.moveCamera(cameraPosition)
            }

            if (state.stops.isEmpty()) {
                map.clear()
                markers.clear()
                return
            }

            state.stops.forEach { stop ->
                if (!markers.containsKey(stop.id)) {
                    val markerOptions = MarkerOptions(stop.location, stop.title)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    val marker = map.addMarker(markerOptions)
                    marker.tag = stop.id
                    markers[stop.id] = marker
                }
            }
        }
    }
}