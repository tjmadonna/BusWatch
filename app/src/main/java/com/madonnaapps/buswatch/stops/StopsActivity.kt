/*
 * Copyright 2017 Tyler Madonna
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.madonnaapps.buswatch.stops

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.Marker
import com.madonnaapps.buswatch.R
import com.madonnaapps.buswatch.data.local.Stop
import dagger.android.AndroidInjection
import javax.inject.Inject

internal class StopsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, StopsActivity::class.java)
        }
    }

    @Inject
    lateinit var viewModel: StopsViewModel

    private lateinit var googleMap: GoogleMap

    private val markers = HashMap<Long, Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stops)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    // Map is ready
    override fun onMapReady(map: GoogleMap) {

        googleMap = map

        val latLng = LatLng(40.4406, -79.9959)

        val update = CameraUpdateFactory.newLatLngZoom(latLng, 15.0f)

        googleMap.moveCamera(update)

        googleMap.setOnCameraIdleListener({

            val bounds = googleMap.projection.visibleRegion.latLngBounds

            val zoom = googleMap.cameraPosition.zoom

            Log.d("StopsActivity", "View zoom is " + map.cameraPosition.zoom)

            viewModel.loadStopsInBounds(bounds, zoom)

        })

        viewModel.stops().observe(this, Observer<List<Stop>> { stops ->

            if (stops == null) {
                markers.clear()
                googleMap.clear()
                return@Observer
            }

            stops.filter { stop ->

                !markers.containsKey(stop.code)

            }.forEach { stop ->

                val markerOptions = stop.toGoogleMapsMarkerOptions()

                val marker = googleMap.addMarker(markerOptions)

                markers.put(stop.code, marker)

            }

        })

    }

}
