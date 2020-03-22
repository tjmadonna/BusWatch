package com.madonnaapps.buswatch.ui.common.extension

import androidx.fragment.app.Fragment
import com.madonnaapps.buswatch.di.component.ApplicationComponent
import com.madonnaapps.buswatch.ui.main.MainActivity
import com.madonnaapps.buswatch.ui.common.navigation.NavigationCoordinator

// Fragment Extensions

val Fragment.applicationComponent: ApplicationComponent
    get() = (requireActivity()).applicationComponent

val Fragment.navigationCoordinator: NavigationCoordinator
    get() = (requireActivity() as MainActivity)