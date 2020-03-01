package com.madonnaapps.buswatch.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.domain.usecase.stop.GetFavoriteStopsUseCase
import com.madonnaapps.buswatch.ui.favorites.contract.FavoritesState
import com.madonnaapps.buswatch.ui.favorites.contract.FavoritesState.DataFavoritesState
import com.madonnaapps.buswatch.ui.favorites.contract.FavoritesState.EmptyDataFavoritesState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val getFavoriteStopsUseCase: GetFavoriteStopsUseCase
) : ViewModel() {

    private val _state = MutableLiveData<FavoritesState>()

    val state: LiveData<FavoritesState>
        get() = _state

    init {
        getFavoriteStopsUseCase.execute(GetFavoriteStopsSubscriber())
    }

    override fun onCleared() {
        getFavoriteStopsUseCase.dispose()
        super.onCleared()
    }

    // Disposable Observers

    private inner class GetFavoriteStopsSubscriber() : DisposableObserver<List<Stop>>() {

        override fun onNext(t: List<Stop>) {
            if (t.isNotEmpty()) {
                _state.value = DataFavoritesState(t)
            } else {
                _state.value = EmptyDataFavoritesState
            }
        }

        override fun onError(e: Throwable) = Unit

        override fun onComplete() = Unit
    }
}