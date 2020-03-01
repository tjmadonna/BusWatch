package com.madonnaapps.buswatch.di.module

import android.app.Application
import android.content.Context
import com.madonnaapps.buswatch.config.thread.UIThread
import com.madonnaapps.buswatch.config.version.StopJsonFileVersion
import com.madonnaapps.buswatch.data.version.StopFileVersion
import com.madonnaapps.buswatch.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindApplicationContext(application: Application): Context

    // Configuration

    @Binds
    abstract fun bindPostExecutionThread(thread: UIThread): PostExecutionThread

    @Binds
    abstract fun bindStopFileVersion(version: StopJsonFileVersion): StopFileVersion
}