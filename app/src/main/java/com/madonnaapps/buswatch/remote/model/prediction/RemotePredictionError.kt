package com.madonnaapps.buswatch.remote.model.prediction

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemotePredictionError(

    @Json(name = "msg")
    val message: String?

)