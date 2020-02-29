package com.madonnaapps.buswatch.file.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class DeserializedStopsResult(
    @Json(name = "stops")
    val stops: List<DeserializedStop>?
)