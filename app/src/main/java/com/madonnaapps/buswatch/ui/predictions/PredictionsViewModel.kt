package com.madonnaapps.buswatch.ui.predictions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.madonnaapps.buswatch.domain.model.Prediction
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.domain.usecase.prediction.GetPredictionsForStopIdUseCase
import com.madonnaapps.buswatch.domain.usecase.stop.FavoriteStopUseCase
import com.madonnaapps.buswatch.domain.usecase.stop.GetStopByIdUseCase
import com.madonnaapps.buswatch.domain.usecase.stop.UnfavoriteStopUseCase
import com.madonnaapps.buswatch.ui.common.extension.ifNotNull
import com.madonnaapps.buswatch.ui.predictions.contract.PredictionsState
import com.madonnaapps.buswatch.ui.predictions.contract.PredictionsState.*
import com.madonnaapps.buswatch.ui.predictions.contract.StopIntent
import com.madonnaapps.buswatch.ui.predictions.contract.StopIntent.ToggleFavoriteStopIntent
import com.madonnaapps.buswatch.ui.predictions.contract.StopState
import com.madonnaapps.buswatch.ui.predictions.mapper.PredictionItemViewModelMapper
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PredictionsViewModel @Inject constructor(
    private val getStopByIdUseCase: GetStopByIdUseCase,
    private val getPredictionsForStopIdUseCase: GetPredictionsForStopIdUseCase,
    private val favoriteStopUseCase: FavoriteStopUseCase,
    private val unfavoriteStopUseCase: UnfavoriteStopUseCase,
    private val predictionsMapper: PredictionItemViewModelMapper
) : ViewModel() {

    // State

    private val _predictionsState = MutableLiveData<PredictionsState>(LoadingPredictionsState)

    val predictionsState: LiveData<PredictionsState>
        get() = _predictionsState

    private val _stopState = MutableLiveData<StopState>(StopState(null, false))

    val stopState: LiveData<StopState>
        get() = _stopState

    // Properties

    private var stopId: String? = null

    private var timerDisposable: Disposable? = null

    fun injectStopId(stopId: String?) = stopId?.let { id ->
        if (this.stopId != null) {
            return@let
        }
        this.stopId = stopId

        getStopByIdUseCase.execute(
            GetStopByIdSubscriber(),
            GetStopByIdUseCase.Params(id)
        )

        timerDisposable = Observable.interval(0, 10, TimeUnit.SECONDS)
            .doOnEach {
                getPredictionsForStopIdUseCase.clear()
                getPredictionsForStopIdUseCase.execute(
                    GetPredictionsSubscriber(),
                    GetPredictionsForStopIdUseCase.Params(stopId)
                )
            }.subscribe()
    }

    fun handleStopIntent(intent: StopIntent) {
        when (intent) {
            is ToggleFavoriteStopIntent -> handleToggleFavoriteStopIntent()
        }
    }

    private fun handleToggleFavoriteStopIntent() {
        ifNotNull(_stopState.value, stopId) { state, stopId ->
            unfavoriteStopUseCase.clear()
            favoriteStopUseCase.clear()
            when (state.isFavorite) {
                true -> unfavoriteStopUseCase.execute(
                    FavoriteStopSubscriber(),
                    UnfavoriteStopUseCase.Params(stopId)
                )
                false -> favoriteStopUseCase.execute(
                    FavoriteStopSubscriber(),
                    FavoriteStopUseCase.Params(stopId)
                )
            }
        }
    }

    override fun onCleared() {
        timerDisposable?.dispose()
        getPredictionsForStopIdUseCase.dispose()
        getStopByIdUseCase.dispose()
        favoriteStopUseCase.dispose()
        unfavoriteStopUseCase.dispose()
        super.onCleared()
    }

    // Disposable Observers

    private inner class GetPredictionsSubscriber : DisposableSingleObserver<List<Prediction>>() {

        @ExperimentalStdlibApi
        override fun onSuccess(t: List<Prediction>) {
            if (t.isNotEmpty()) {
                _predictionsState.value = DataPredictionsState(
                    data = t.map { predictionsMapper.mapToPredictionItemViewModel(it) }
                )
            } else {
                _predictionsState.value = ErrorPredictionsState("No Predictions Available")
            }
        }

        override fun onError(e: Throwable) {
            _predictionsState.value = ErrorPredictionsState(e.localizedMessage!!)
        }
    }

    private inner class GetStopByIdSubscriber : DisposableObserver<Stop>() {

        override fun onNext(t: Stop) {
            _stopState.value = StopState(t.title, t.favorite)
        }

        override fun onError(e: Throwable) = Unit

        override fun onComplete() = Unit
    }

    private inner class FavoriteStopSubscriber : DisposableCompletableObserver() {

        override fun onComplete() = Unit

        override fun onError(e: Throwable) = Unit
    }
}