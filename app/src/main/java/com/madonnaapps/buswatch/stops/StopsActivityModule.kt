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

package com.madonnaapps.buswatch.stops

import androidx.lifecycle.ViewModelProviders
import com.madonnaapps.buswatch.di.ActivityScope
import com.madonnaapps.buswatch.utils.BusWatchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal class StopsActivityModule {

    @Provides
    @ActivityScope
    fun provideStopsViewModel(activity: StopsActivity, viewModelFactory: BusWatchViewModelFactory) : StopsViewModel {
        return ViewModelProviders
                .of(activity, viewModelFactory)
                .get(StopsViewModel::class.java)
    }

}