package com.madonnaapps.buswatch.domain.model

import java.util.*

data class Prediction(
    val vehicleId: String,
    val arrivalTime: Date,
    val route: String,
    val destination: String,
    val direction: String
)