package com.madonnaapps.buswatch.domain.model

import java.util.*

data class Prediction(
    val arrivalTime: Date,
    val route: String,
    val destination: String
)