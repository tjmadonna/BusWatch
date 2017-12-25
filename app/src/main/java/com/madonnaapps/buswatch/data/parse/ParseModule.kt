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

package com.madonnaapps.buswatch.data.parse

import android.content.Context
import com.madonnaapps.buswatch.R
import com.madonnaapps.buswatch.di.AppScope
import dagger.Module
import dagger.Provides
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader

@Module
internal class ParseModule {

    @Provides
    @AppScope
    fun provideInputStream(context: Context): InputStream {
        return context.resources.openRawResource(R.raw.stops)
    }

    @Provides
    @AppScope
    fun provideReader(inputStream: InputStream): Reader {
        return InputStreamReader(inputStream)
    }

}