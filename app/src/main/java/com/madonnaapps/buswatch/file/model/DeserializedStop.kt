package com.madonnaapps.buswatch.file.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeserializedStop(

    @Json(name = "id")
    val id: String?,

    @Json(name = "code")
    val code: String?,

    @Json(name = "title")
    val title: String?,

    @Json(name = "latitude")
    val latitude: Double?,

    @Json(name = "longitude")
    val longitude: Double?,

    @Json(name = "routes")
    val routes: List<String>?

)