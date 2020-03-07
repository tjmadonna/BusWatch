package com.madonnaapps.buswatch.remote.model.prediction

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemotePredictionResponse(

    @Json(name = "bustime-response")
    val result: RemotePredictionResult? = null

)