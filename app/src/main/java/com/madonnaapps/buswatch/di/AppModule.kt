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

package com.madonnaapps.buswatch.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.madonnaapps.buswatch.data.local.RoomDatabaseModule
import com.madonnaapps.buswatch.data.parse.ParseModule
import com.madonnaapps.buswatch.data.remote.RetrofitModule
import dagger.Module
import dagger.Provides

@Module(includes = [RoomDatabaseModule::class, ParseModule::class, RetrofitModule::class])

internal class AppModule {

    @Provides
    @AppScope
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @AppScope
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @AppScope
    fun provideGson(): Gson {
        return GsonBuilder()
                .setDateFormat("yyyyMMdd HH:mm")
                .create()
    }

}