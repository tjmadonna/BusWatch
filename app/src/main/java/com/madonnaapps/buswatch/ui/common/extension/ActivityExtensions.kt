package com.madonnaapps.buswatch.ui.common.extension

import android.app.Activity
import com.madonnaapps.buswatch.BusWatchApplication
import com.madonnaapps.buswatch.di.component.ApplicationComponent

// Activity Extensions

val Activity.applicationComponent: ApplicationComponent
    get() = (application as BusWatchApplication).applicationComponent