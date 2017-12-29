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

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds
import com.madonnaapps.buswatch.data.MapsRepository
import com.madonnaapps.buswatch.data.StopsRepository
import com.madonnaapps.buswatch.data.local.Stop

internal class StopsViewModel constructor(private val stopsRepository: StopsRepository,
                                          private val mapsRepository: MapsRepository) : ViewModel() {

    companion object {

        private val ZOOM_MIN_THRESHOLD = 15

    }

    private val stops = MutableLiveData<List<Stop>>()

    fun stops(): LiveData<List<Stop>> {
        return stops
    }

    fun loadStopsInBounds(bounds: LatLngBounds, zoom: Float) {

        if (zoom < ZOOM_MIN_THRESHOLD) {
            stops.postValue(null)
            return
        }

        AsyncTask.execute({

            val stopsInBounds = stopsRepository.getStopsInBounds(
                    bounds.northeast.latitude,
                    bounds.southwest.latitude,
                    bounds.northeast.longitude,
                    bounds.southwest.longitude)

            stops.postValue(stopsInBounds)

        })

    }

    fun saveCameraPosition(cameraPosition: CameraPosition) {
        mapsRepository.cameraPosition = cameraPosition
    }

    fun cameraPosition() : LiveData<CameraPosition> {

        val position = MutableLiveData<CameraPosition>()

        AsyncTask.execute {

            position.postValue(mapsRepository.cameraPosition)

        }

        return position
    }

}