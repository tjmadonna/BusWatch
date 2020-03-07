package com.madonnaapps.buswatch.ui.predictions.model

sealed class ArriveTime {
    object NowArriveTime : ArriveTime()
    object OneMinuteArriveTime: ArriveTime()
    data class ManyMinutesArriveTime(val minutes: Long): ArriveTime()
}

data class PredictionItemViewModel(
    val id: String,
    val route: String,
    val destination: String,
    val timeToArrival: ArriveTime
)