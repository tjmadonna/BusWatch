package com.madonnaapps.buswatch.ui.favorites.contract

import com.madonnaapps.buswatch.domain.model.Stop

sealed class FavoritesIntent {

}

sealed class FavoritesState {
    data class DataFavoritesState(val data: List<Stop>) : FavoritesState()
    object EmptyDataFavoritesState : FavoritesState()
}