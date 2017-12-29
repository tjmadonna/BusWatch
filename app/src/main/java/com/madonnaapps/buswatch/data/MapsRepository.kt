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

package com.madonnaapps.buswatch.data

import android.content.SharedPreferences
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import java.util.function.DoubleToLongFunction
import java.util.function.LongToDoubleFunction
import javax.inject.Inject

class MapsRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private companion object {

        private val MAPS_LOCATION_LATITUDE_KEY = "maps_location_latitude"

        private val MAPS_LOCATION_LONGITUDE_KEY = "maps_location_longitude"

        private val MAPS_ZOOM_KEY = "maps_zoom"

        private val DEFAULT_LATITUDE = 40.4406

        private val DEFAULT_LONGITUDE = -79.9959

        private val DEFAULT_ZOOM = 15.0f

    }

    var cameraPosition: CameraPosition

        get() {

            val latLong = sharedPreferences.getLong(MAPS_LOCATION_LATITUDE_KEY,
                    DEFAULT_LATITUDE.toBits())
            val lat = Double.fromBits(latLong)

            val lonLong = sharedPreferences.getLong(MAPS_LOCATION_LONGITUDE_KEY,
                    DEFAULT_LONGITUDE.toBits())
            val lon = Double.fromBits(lonLong)

            val zoom = sharedPreferences.getFloat(MAPS_ZOOM_KEY, DEFAULT_ZOOM)

            return CameraPosition.fromLatLngZoom(LatLng(lat, lon), zoom)

        }

        set(value) {

            val lat = value.target.latitude.toBits()

            val lon = value.target.longitude.toBits()

            sharedPreferences.edit()
                    .putLong(MAPS_LOCATION_LATITUDE_KEY, lat)
                    .putLong(MAPS_LOCATION_LONGITUDE_KEY, lon)
                    .putFloat(MAPS_ZOOM_KEY, value.zoom)
                    .apply()

        }

}