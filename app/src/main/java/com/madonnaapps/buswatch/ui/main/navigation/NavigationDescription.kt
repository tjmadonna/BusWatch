package com.madonnaapps.buswatch.ui.main.navigation

sealed class NavigationDescription {
    data class PredictionsNavigationDescription(val stopId: String) : NavigationDescription()
}