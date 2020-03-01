package com.madonnaapps.buswatch.file.datastore

import android.content.Context
import com.madonnaapps.buswatch.data.datastore.stop.StopFileDataStore
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.file.mapper.FileMapper
import com.madonnaapps.buswatch.file.model.DeserializedStop
import com.madonnaapps.buswatch.file.model.DeserializedStopsResult
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.reactivex.Single
import okio.Okio
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.lang.IllegalStateException
import javax.inject.Inject

class StopFileDataStoreImpl @Inject constructor(
    private val moshi: Moshi,
    private val context: Context,
    private val mapper: FileMapper<DeserializedStop, Stop?>
) : StopFileDataStore {

    companion object {
        private const val FILE_NAME = "stops.json"
    }

    private val jsonAdapter: JsonAdapter<DeserializedStopsResult> by lazy {
        return@lazy moshi.adapter(DeserializedStopsResult::class.java)
    }

    override fun getStops(): Single<List<Stop>> {
        return Single.fromCallable {
            return@fromCallable getStopsFromFile()
        }
    }

    @Throws(IOException::class, FileNotFoundException::class, IllegalStateException::class)
    private fun getStopsFromFile(): List<Stop> {

        var inputStream: InputStream? = null

        try {
            inputStream = context.applicationContext.assets.open(FILE_NAME)
            val source = Okio.source(inputStream)
            val bufferedReader = Okio.buffer(source)
            val stops = jsonAdapter.fromJson(bufferedReader)?.stops
                ?: throw IllegalStateException(
                    "JsonAdapter returned null stops without throwing an error"
                )
            return stops.mapNotNull { stop -> mapper.mapFromFileObject(stop) }

        } catch (e: IOException) {
            throw e
        } catch (e: FileNotFoundException) {
            throw e
        } catch (e: IllegalStateException) {
            throw e
        } finally {
            inputStream?.close()
        }
    }
}