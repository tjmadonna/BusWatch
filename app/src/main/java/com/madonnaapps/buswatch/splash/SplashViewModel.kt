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

package com.madonnaapps.buswatch.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.madonnaapps.buswatch.data.StopsRepository
import com.madonnaapps.buswatch.data.parse.ParseResult

internal class SplashViewModel constructor(stopsRepository: StopsRepository) : ViewModel() {

    private val parseResult = stopsRepository.stopsOnLatestFileVersion()

    fun parseResult() : LiveData<ParseResult> {
        return parseResult
    }

}