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

package com.madonnaapps.buswatch.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
internal interface StopDao {

    @Query("SELECT code title latitude longitude WHERE latitude <= :northBound " +
            "AND latitude >= :southBound AND longitude <= :eastBound AND longitude >= :westBound")
    fun getStopsInBounds(northBound: Double, southBound: Double,
                         eastBound: Double, westBound: Double ) : List<Stop>

    @Query("SELECT code title latitude longitude WHERE code == :code")
    fun getStopByCode(code: Long) : Stop

    @Query("DELETE FROM stops")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stops: List<Stop>) : List<Long>

}