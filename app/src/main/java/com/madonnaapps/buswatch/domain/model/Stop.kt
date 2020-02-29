package com.madonnaapps.buswatch.domain.model

data class Stop(
    val id: String,
    val code: String,
    val title: String,
    val favorite: Boolean,
    val location: Location,
    val routes: List<String>
)