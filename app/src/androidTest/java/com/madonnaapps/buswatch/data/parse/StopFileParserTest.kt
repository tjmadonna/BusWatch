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

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.google.gson.Gson
import com.madonnaapps.buswatch.R
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStreamReader

@RunWith(AndroidJUnit4::class)
class StopFileParserTest {

    private lateinit var stopFileParser: StopFileParser

    @Before
    fun setUp() {

        stopFileParser = StopFileParser()

        val appContext = InstrumentationRegistry.getTargetContext()

        val inputStream = appContext.resources.openRawResource(R.raw.stops)

        stopFileParser.reader = InputStreamReader(inputStream)

        stopFileParser.gson = Gson()

    }

    @After
    fun tearDown() {

    }

    @Test
    fun parseTest() {

        val stops = stopFileParser.parse()

        Assert.assertNotNull(stops)

    }

}
