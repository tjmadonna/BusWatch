package com.madonnaapps.buswatch.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madonnaapps.buswatch.di.factory.ViewModelProviderFactory
import com.madonnaapps.buswatch.di.key.ViewModelKey
import com.madonnaapps.buswatch.ui.favorites.FavoritesViewModel
import com.madonnaapps.buswatch.ui.predictions.PredictionsViewModel
import com.madonnaapps.buswatch.ui.stopmap.StopMapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelProviderFactory(
        factory: ViewModelProviderFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(StopMapViewModel::class)
    abstract fun bindStopMapViewModel(viewModel: StopMapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PredictionsViewModel::class)
    abstract fun bindPredictionsViewModel(viewModel: PredictionsViewModel): ViewModel
}