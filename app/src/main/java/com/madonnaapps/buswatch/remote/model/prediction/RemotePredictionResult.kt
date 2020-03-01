package com.madonnaapps.buswatch.remote.model.prediction

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemotePredictionResult(

    @Json(name = "prd")
    val predictions: List<RemotePrediction>?,

    @Json(name = "error")
    val error: List<RemotePredictionError>?

)
