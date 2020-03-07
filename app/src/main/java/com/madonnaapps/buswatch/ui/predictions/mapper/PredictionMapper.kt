package com.madonnaapps.buswatch.ui.predictions.mapper

import com.madonnaapps.buswatch.domain.model.Prediction
import com.madonnaapps.buswatch.ui.predictions.model.ArriveTime.*
import com.madonnaapps.buswatch.ui.predictions.model.PredictionItemViewModel
import java.util.*
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject
import kotlin.math.max

class PredictionItemViewModelMapper @Inject constructor() {

    @ExperimentalStdlibApi
    fun mapToPredictionItemViewModel(prediction: Prediction): PredictionItemViewModel {
        return PredictionItemViewModel(
            id = prediction.vehicleId,
            route = prediction.route,
            destination = formatDestinationString(prediction.destination, prediction.direction),
            timeToArrival = getArriveTime(prediction.arrivalTime.time)
        )
    }

    @ExperimentalStdlibApi
    private fun formatDestinationString(destination: String, direction: String): String {
        val formatDir = direction.toLowerCase(Locale.getDefault()).capitalize(Locale.getDefault())
        return "$destination - $formatDir"
    }

    private fun getArriveTime(arriveTime: Long) =
        when (val seconds = max(MILLISECONDS.toSeconds(arriveTime - Date().time), 0)) {
            in Int.MIN_VALUE..30 -> NowArriveTime
            in 31..119 -> OneMinuteArriveTime
            else -> ManyMinutesArriveTime(seconds / 60)
        }
}