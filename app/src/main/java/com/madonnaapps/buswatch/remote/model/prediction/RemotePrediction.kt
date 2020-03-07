package com.madonnaapps.buswatch.remote.model.prediction

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemotePrediction(

    @Json(name = "vid")
    val vehicleId: String? = null,

    @Json(name = "prdtm")
    val arrivalTime: String? = null,

    @Json(name = "rt")
    val route: String? = null,

    @Json(name = "des")
    val destination: String? = null,

    @Json(name = "rtdir")
    val direction: String? = null

)