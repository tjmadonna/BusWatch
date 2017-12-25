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

package com.madonnaapps.buswatch.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import android.os.AsyncTask
import com.madonnaapps.buswatch.data.local.StopDao
import com.madonnaapps.buswatch.data.parse.ParseResult
import com.madonnaapps.buswatch.data.parse.StopFileParser
import javax.inject.Inject

internal class StopsRepository @Inject constructor(private val stopDao: StopDao,
                                           private val sharedPreferences: SharedPreferences,
                                           private val stopFileParser: StopFileParser) {

    private companion object {

        // Stop data version. Increment this integer when updating stops.json file
        // in res/raw directory
        private val STOP_FILE_VERSION = 1

        // Stop data version shared preferences key
        private val STOP_FILE_VERSION_PREF_KEY = "stops_file_version"

    }

    /*
     * Function that checks if database has the latest list of stops. Updates the data if it is not
     * the latest version
     *
     * @return LiveData object that carries the parsing result
     */
    fun stopsOnLatestFileVersion(): LiveData<ParseResult> {

        val liveDataResult = MutableLiveData<ParseResult>()

        // Check if the current stops data version is up to date
        val storeFileVersion = sharedPreferences.getInt(STOP_FILE_VERSION_PREF_KEY, 0)

        if (storeFileVersion != STOP_FILE_VERSION) {

            // Stop data is not up to date. Parse the new stop json file
            parseStopsFromLocalJsonFileAndSave(liveDataResult)

        } else {

            // Stop data is up to date. Return result with no error
            liveDataResult.value = ParseResult()

        }

        return liveDataResult

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
            stopDao.deleteAll()

            // Parse the json file and convert results to stop database objects
            val stops = stopFileParser.parse()
                    .map { parseStop -> parseStop.toDatabaseStop() }

            // Insert stops into the database
            val insertedIds = stopDao.insert(stops)

            if (insertedIds.count() > 0) {

                // Success: Stops were inserted. Update the current stops data version
                // We are not on the main thread.
                sharedPreferences.edit()
                        .putInt(STOP_FILE_VERSION_PREF_KEY, STOP_FILE_VERSION)
                        .apply()

            } else {

                // Error: No stops were inserted in the database, set the error in parse result
                parseResult.error = Exception("No stops inserted into database")

            }

            // Post parse result to live data. We are not on the main thread
            liveData.postValue(parseResult)

        })

    }

}