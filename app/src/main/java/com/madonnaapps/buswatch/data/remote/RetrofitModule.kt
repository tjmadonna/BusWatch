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

package com.madonnaapps.buswatch.data.remote

import com.google.gson.Gson
import com.madonnaapps.buswatch.BuildConfig
import com.madonnaapps.buswatch.di.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
internal class RetrofitModule {

    @Provides
    @AppScope
    fun provideTrueTimeApiService(retrofit: Retrofit): TrueTimeApiService {
        return retrofit.create(TrueTimeApiService::class.java)
    }

    @Provides
    @AppScope
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.TRUE_TIME_API_BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .build()
    }

    @Provides
    @AppScope
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

}