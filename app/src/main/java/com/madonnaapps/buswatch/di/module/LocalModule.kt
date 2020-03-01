package com.madonnaapps.buswatch.di.module

import android.content.Context
import com.madonnaapps.buswatch.data.datastore.location.LocationLocalDataStore
import com.madonnaapps.buswatch.data.datastore.stop.StopLocalDataStore
import com.madonnaapps.buswatch.domain.model.LocationZoom
import com.madonnaapps.buswatch.domain.model.Stop
import com.madonnaapps.buswatch.local.dao.FavoriteStopDao
import com.madonnaapps.buswatch.local.dao.LastLocationDao
import com.madonnaapps.buswatch.local.dao.StopDao
import com.madonnaapps.buswatch.local.dao.StopVersionDao
import com.madonnaapps.buswatch.local.database.BusWatchRoomDatabase
import com.madonnaapps.buswatch.local.datastore.LocationLocalDataStoreImpl
import com.madonnaapps.buswatch.local.datastore.StopLocalDataStoreImpl
import com.madonnaapps.buswatch.local.mapper.*
import com.madonnaapps.buswatch.local.model.FavoriteWithStopDbo
import com.madonnaapps.buswatch.local.model.LastLocationDbo
import com.madonnaapps.buswatch.local.model.StopDbo
import com.madonnaapps.buswatch.local.model.StopWithFavoriteDbo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class LocalModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideDatabase(context: Context): BusWatchRoomDatabase {
            return BusWatchRoomDatabase.getInstance(context.applicationContext)
        }

        @Provides
        @JvmStatic
        fun provideFavoriteStopDao(database: BusWatchRoomDatabase): FavoriteStopDao {
            return database.favoriteStopDap()
        }

        @Provides
        @JvmStatic
        fun provideLastLocationDao(database: BusWatchRoomDatabase): LastLocationDao {
            return database.lastLocationDao()
        }

        @Provides
        @JvmStatic
        fun provideStopDao(database: BusWatchRoomDatabase): StopDao {
            return database.stopDao()
        }

        @Provides
        @JvmStatic
        fun provideStopVersionDao(database: BusWatchRoomDatabase): StopVersionDao {
            return database.stopVersionDao()
        }
    }

    @Binds
    abstract fun bindStopLocalDataStore(
        dataStore: StopLocalDataStoreImpl
    ): StopLocalDataStore

    @Binds
    abstract fun bindFavoriteWithStopDboMapper(
        mapper: FavoriteWithStopDboMapper
    ): CacheMapper<FavoriteWithStopDbo, Stop>

    @Binds
    abstract fun bindLastLocationDboMapper(
        mapper: LastLocationDboMapper
    ): CacheMapper<LastLocationDbo, LocationZoom>

    @Binds
    abstract fun bindStopDboMapper(
        mapper: StopDboMapper
    ): CacheMapper<StopDbo, Stop>

    @Binds
    abstract fun bindStopWithFavoriteDboMapper(
        mapper: StopWithFavoriteDboMapper
    ): CacheMapper<StopWithFavoriteDbo, Stop>
}