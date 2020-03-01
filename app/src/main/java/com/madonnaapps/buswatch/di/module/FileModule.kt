package com.madonnaapps.buswatch.di.module

import android.content.Context
import com.madonnaapps.buswatch.data.datastore.stop.StopFileDataStore
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.file.datastore.StopFileDataStoreImpl
import com.madonnaapps.buswatch.file.mapper.DeserializedStopMapper
import com.madonnaapps.buswatch.file.mapper.FileMapper
import com.madonnaapps.buswatch.file.model.DeserializedStop
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class FileModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideMoshi(context: Context): Moshi {
            return Moshi.Builder()
                .build()
        }
    }

    @Binds
    abstract fun bindStopFileDataStore(
        dataStore: StopFileDataStoreImpl
    ): StopFileDataStore

    @Binds
    abstract fun bindFavoriteWithStopDboMapper(
        mapper: DeserializedStopMapper
    ): FileMapper<DeserializedStop, Stop?>
}
