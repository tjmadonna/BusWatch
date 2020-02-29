/*
 * Copyright 2017 Tyler Madonna
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.madonnaapps.buswatch.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madonnaapps.buswatch.data.MapsRepository
import com.madonnaapps.buswatch.data.PredictionsRepository
import com.madonnaapps.buswatch.data.StopsRepository
import com.madonnaapps.buswatch.splash.SplashViewModel
import com.madonnaapps.buswatch.stops.StopsViewModel
import javax.inject.Inject

internal class BusWatchViewModelFactory @Inject constructor(private val stopsRepository: StopsRepository,
                                                            private val mapsRepository: MapsRepository,
                                                            private val predictionsRepository: PredictionsRepository
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(stopsRepository) as T

        } else if (modelClass.isAssignableFrom(StopsViewModel::class.java)) {
            return StopsViewModel(stopsRepository, mapsRepository) as T

        } else run { throw IllegalArgumentException("View model cannot be found") }

    }

}