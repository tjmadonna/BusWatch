package com.madonnaapps.buswatch.ui.stopmap.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.madonnaapps.buswatch.R

class StopInfoWindowAdapter constructor(
    private val context: Context
) : GoogleMap.InfoWindowAdapter {


    override fun getInfoContents(marker: Marker?): View? = null

    override fun getInfoWindow(marker: Marker?): View {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.maps_info_window_stops, FrameLayout(context), false)

        view.findViewById<TextView>(R.id.text_maps_info_window_title).text = marker?.title
        view.findViewById<TextView>(R.id.text_maps_info_window_description).text = marker?.snippet

        return view
    }
}