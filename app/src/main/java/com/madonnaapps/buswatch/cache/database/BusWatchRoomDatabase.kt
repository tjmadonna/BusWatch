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

package com.madonnaapps.buswatch.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.madonnaapps.buswatch.data.local.Stop
import com.madonnaapps.buswatch.data.local.StopDao

@Database(entities = [Stop::class], version = 1)
internal abstract class BusWatchRoomDatabase : RoomDatabase() {

    // Stop dao instance
    abstract fun stopDao(): StopDao

}