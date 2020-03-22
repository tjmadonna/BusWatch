package com.madonnaapps.buswatch.ui.common.navigation

sealed class NavigationDescription {
    data class PredictionsNavigationDescription(val stopId: String) : NavigationDescription()
}