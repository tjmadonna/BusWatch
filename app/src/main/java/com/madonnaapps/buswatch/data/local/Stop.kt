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

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "stops")
internal data class Stop(

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "code") var code: Long = 0,

        @ColumnInfo(name = "title") var title: String = "",

        @ColumnInfo(name = "latitude") var latitude: Double = 0.0,

        @ColumnInfo(name = "longitude") var longitude: Double = 0.0

)