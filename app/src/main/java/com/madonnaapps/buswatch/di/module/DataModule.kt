package com.madonnaapps.buswatch.di.module

import com.madonnaapps.buswatch.data.repository.PredictionRepositoryImpl
import com.madonnaapps.buswatch.data.repository.StopRepositoryImpl
import com.madonnaapps.buswatch.domain.repository.LocationRepository
import com.madonnaapps.buswatch.domain.repository.PredictionRepository
import com.madonnaapps.buswatch.domain.repository.StopRepository
import com.madonnaapps.buswatch.local.datastore.LocationLocalDataStoreImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindLocationRepository(
        dataStore: LocationLocalDataStoreImpl
    ): LocationRepository

    @Binds
    abstract fun bindStopRepository(
        repository: StopRepositoryImpl
    ): StopRepository

    @Binds
    abstract fun bindPredictionRepository(
        repository: PredictionRepositoryImpl
    ): PredictionRepository
}