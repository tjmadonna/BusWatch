package com.madonnaapps.buswatch.ui.predictions.contract

import com.madonnaapps.buswatch.ui.predictions.model.PredictionItemViewModel

sealed class StopIntent {
    object ToggleFavoriteStopIntent : StopIntent()
}

sealed class PredictionsState {
    object LoadingPredictionsState : PredictionsState()
    data class DataPredictionsState(val data: List<PredictionItemViewModel>) : PredictionsState()
    data class ErrorPredictionsState(val message: String) : PredictionsState()
}

data class StopState(val title: String?, val isFavorite: Boolean)
