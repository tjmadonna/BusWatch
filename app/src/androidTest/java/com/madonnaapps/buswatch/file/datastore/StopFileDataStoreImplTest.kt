package com.madonnaapps.buswatch.file.datastore

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.madonnaapps.buswatch.file.mapper.DeserializedStopMapper
import com.squareup.moshi.Moshi
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StopFileDataStoreImplTest {

    private lateinit var stopFileDataStoreImpl: StopFileDataStoreImpl

    @Before
    fun setup() {
        stopFileDataStoreImpl = StopFileDataStoreImpl(
            Moshi.Builder().build(),
            InstrumentationRegistry.getInstrumentation().targetContext,
            DeserializedStopMapper()
        )
    }

    @Test
    fun getStopsReturnsStops() {
        val testObserver = stopFileDataStoreImpl.getStops()
            .test()
            .assertNoErrors()

        val actualValue = testObserver.values().first()

        assertTrue(actualValue.isNotEmpty())
    }

}