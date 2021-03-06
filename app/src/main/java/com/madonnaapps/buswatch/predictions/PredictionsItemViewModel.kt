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

import com.madonnaapps.buswatch.data.remote.TrueTimeApiPrediction
import java.util.*
import java.util.concurrent.TimeUnit

internal class PredictionsItemViewModel {

    var item: TrueTimeApiPrediction? = null

    val route: String
        get() {
            return item?.route ?: ""
        }

    val destination: String
        get() {
            return item?.destination ?: ""
        }

    val arrivalTime: Long?
        get() {

            item?.arrivalTime?.let { time ->

                val diff = time.time - Date().time

                return TimeUnit.MILLISECONDS.toMinutes(diff)


            }

            return null

        }

}