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

package com.madonnaapps.buswatch.data

import com.madonnaapps.buswatch.data.remote.TrueTimeApiResponse
import com.madonnaapps.buswatch.data.remote.TrueTimeApiService
import retrofit2.Call
import javax.inject.Inject

internal class PredictionsRepository @Inject constructor(
        private val trueTimeApiService: TrueTimeApiService) {

    fun predictions(stopCode: Long): Call<TrueTimeApiResponse> {

        return trueTimeApiService.getPredictions(stopCode)

    }

}