package com.madonnaapps.buswatch.ui.main.navigation

sealed class NavigationDescription {
    object MainFragmentNavigationDescription : NavigationDescription()
    data class PredictionsFragmentNavigationDescription(val stopId: String) :
        NavigationDescription()
}