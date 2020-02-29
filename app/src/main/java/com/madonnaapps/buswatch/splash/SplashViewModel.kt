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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.AsyncTask
import com.madonnaapps.buswatch.data.StopsRepository
import com.madonnaapps.buswatch.data.parse.ParseResult

internal class SplashViewModel constructor(private val stopsRepository: StopsRepository) : ViewModel() {

    private companion object {

        // Stop data version. Increment this integer when updating stops.json file
        // in res/raw directory
        private val STOP_FILE_VERSION = 1

    }

    private val parseResult = MutableLiveData<ParseResult>()

    /*
    * Function that checks if database has the latest list of stops. Updates the data if it is not
    * the latest version
    *
    * @return LiveData object that carries the parsing result
    */
    fun parseResult(): LiveData<ParseResult> {

        // Check if the current stops data version is up to date
        val storeFileVersion = stopsRepository.getStopFileVersion()

        if (storeFileVersion != SplashViewModel.STOP_FILE_VERSION) {

            // Stop data is not up to date. Parse the new stop json file
            parseStopsFromLocalJsonFileAndSave(parseResult)

        } else {

            // Stop data is up to date. Return result with no error
            parseResult.value = ParseResult()

        }

        return parseResult

    }

    /*
 * Function that parses the json data file and inserts it into the database. Posts the results
 * to the live data input parameter
 *
 * @params LiveData object that carries the parsing result
 */
    private fun parseStopsFromLocalJsonFileAndSave(liveData: MutableLiveData<ParseResult>) {

        // Execute this on another thread
        AsyncTask.execute({

            // Create a parse result object
            val parseResult = ParseResult()

            // Clear the stop database table because we are doing a full update
            stopsRepository.deleteAllStops()

            // Parse the json file and convert results to stop database objects
            val stops = stopsRepository.parseStopFile()

            // Insert stops into the database
            val insertCount = stopsRepository.insertStops(stops)

            if (insertCount > 0) {

                // Success: Stops were inserted. Update the current stops data version
                stopsRepository.updateStopFileVersion(STOP_FILE_VERSION)

            } else {

                // Error: No stops were inserted in the database, set the error in parse result
                parseResult.error = Exception("No stops inserted into database")

            }

            // Post parse result to live data. We are not on the main thread
            liveData.postValue(parseResult)

        })

    }

}