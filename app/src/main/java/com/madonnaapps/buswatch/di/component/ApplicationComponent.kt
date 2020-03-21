package com.madonnaapps.buswatch.di.component

import android.app.Application
import com.madonnaapps.buswatch.di.module.*
import com.madonnaapps.buswatch.ui.favorites.FavoritesFragment
import com.madonnaapps.buswatch.ui.main.MainActivity
import com.madonnaapps.buswatch.ui.predictions.PredictionsActivity
import com.madonnaapps.buswatch.ui.stopmap.StopMapFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ViewModelModule::class,
        DataModule::class,
        LocalModule::class,
        FileModule::class,
        RemoteModule::class
    ]
)
interface ApplicationComponent {

    // Factory to create instance of ApplicationComponent
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }

    // Classes that can inject into component

    fun inject(mainActivity: MainActivity)

    fun inject(stopMapFragment: StopMapFragment)

    fun inject(favoritesFragment: FavoritesFragment)

    fun inject(predictionsActivity: PredictionsActivity)

}