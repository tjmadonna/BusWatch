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

import android.content.SharedPreferences
import com.madonnaapps.buswatch.data.local.Stop
import com.madonnaapps.buswatch.data.local.StopDao
import com.madonnaapps.buswatch.data.parse.StopFileParser
import javax.inject.Inject

internal class StopsRepository @Inject constructor(private val stopDao: StopDao,
                                                   private val sharedPreferences: SharedPreferences,
                                                   private val stopFileParser: StopFileParser) {

    private companion object {

        // Stop data version shared preferences key
        private val STOP_FILE_VERSION_PREF_KEY = "stops_file_version"

    }

    // Stop database

    fun insertStops(stops: List<Stop>) : Int {
        val indicesInserted = stopDao.insert(stops)
        return indicesInserted.count()
    }

    fun deleteAllStops() {
        stopDao.deleteAll()
    }

    // Shared preferences

    fun getStopFileVersion() : Int {
        return sharedPreferences.getInt(StopsRepository.STOP_FILE_VERSION_PREF_KEY, 0)
    }

    fun updateStopFileVersion(version : Int) {
        sharedPreferences.edit()
                .putInt(StopsRepository.STOP_FILE_VERSION_PREF_KEY, version)
                .apply()
    }

    // Stop parsing from Json file

    fun parseStopFile() : List<Stop> {
        return stopFileParser.parse()
                .map { parseStop -> parseStop.toDatabaseStop() }
    }

}