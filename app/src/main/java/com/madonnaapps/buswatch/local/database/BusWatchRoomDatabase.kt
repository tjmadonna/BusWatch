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

package com.madonnaapps.buswatch.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.madonnaapps.buswatch.local.converter.RouteTypeConverter
import com.madonnaapps.buswatch.local.dao.LastLocationDao
import com.madonnaapps.buswatch.local.dao.StopDao
import com.madonnaapps.buswatch.local.dao.StopVersionDao
import com.madonnaapps.buswatch.local.model.FavoriteStopDbo
import com.madonnaapps.buswatch.local.model.LastLocationDbo
import com.madonnaapps.buswatch.local.model.StopDbo
import com.madonnaapps.buswatch.local.model.StopVersionDbo

@Database(
    entities = [
        StopDbo::class,
        StopVersionDbo::class,
        FavoriteStopDbo::class,
        LastLocationDbo::class
    ],
    version = 2
)
// TODO: Handle database migration
@TypeConverters(RouteTypeConverter::class)
abstract class BusWatchRoomDatabase : RoomDatabase() {

    abstract fun stopDao(): StopDao

    abstract fun stopVersionDao(): StopVersionDao

    abstract fun lastLocationDao(): LastLocationDao

    companion object {

        private const val DB_NAME = "bus-watch-database"

        private var INSTANCE: BusWatchRoomDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): BusWatchRoomDatabase {
            // Double-checked locking just in case called from other threads
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            BusWatchRoomDatabase::class.java, DB_NAME
                        )
                            .build()
                    }
                    return INSTANCE as BusWatchRoomDatabase
                }
            }
            return INSTANCE as BusWatchRoomDatabase
        }
    }
}