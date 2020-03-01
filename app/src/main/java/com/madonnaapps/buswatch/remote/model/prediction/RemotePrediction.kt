package com.madonnaapps.buswatch.remote.model.prediction

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemotePrediction(

    @Json(name = "vid")
    val vehicleId: String?,

    @Json(name = "prdtm")
    val arrivalTime: Long?,

    @Json(name = "rt")
    val route: String?,

    @Json(name = "des")
    val destination: String?,

    @Json(name = "rtdir")
    val direction: String?

)