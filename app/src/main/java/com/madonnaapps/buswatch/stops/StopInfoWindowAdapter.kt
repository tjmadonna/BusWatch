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

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

import com.madonnaapps.buswatch.R

internal class StopInfoWindowAdapter constructor(private val context: Context) : GoogleMap.InfoWindowAdapter {


    override fun getInfoContents(marker: Marker?): View? {

        return null

    }

    override fun getInfoWindow(marker: Marker?): View {

        val view = LayoutInflater.from(context).inflate(R.layout.maps_info_window_stops,
                FrameLayout(context), false)

        marker?.title.let { title ->

            view.findViewById<TextView>(R.id.maps_info_window_title).text = title

        }

        return view
    }

}