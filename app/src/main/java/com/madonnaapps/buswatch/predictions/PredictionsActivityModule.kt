/*
 * Copyright 2018 Tyler Madonna
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

package com.madonnaapps.buswatch.predictions

import android.arch.lifecycle.ViewModelProviders
import android.gesture.Prediction
import android.util.Log
import com.madonnaapps.buswatch.di.ActivityScope
import com.madonnaapps.buswatch.utils.BusWatchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal class PredictionsActivityModule {

    @Provides
    @ActivityScope
    fun providePredictionsViewModel(activity: PredictionsActivity, viewModelFactory: BusWatchViewModelFactory): PredictionsViewModel {
        return ViewModelProviders
                .of(activity, viewModelFactory)
                .get(PredictionsViewModel::class.java)
    }

}