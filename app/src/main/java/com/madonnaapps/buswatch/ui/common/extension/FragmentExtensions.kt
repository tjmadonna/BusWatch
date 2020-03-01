package com.madonnaapps.buswatch.ui.common.extension

import androidx.fragment.app.Fragment
import com.madonnaapps.buswatch.di.component.ApplicationComponent

// Fragment Extensions

val Fragment.applicationComponent: ApplicationComponent
    get() = (requireActivity()).applicationComponent