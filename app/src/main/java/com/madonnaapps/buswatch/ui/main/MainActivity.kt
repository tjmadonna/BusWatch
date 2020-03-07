package com.madonnaapps.buswatch.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.madonnaapps.buswatch.R
import com.madonnaapps.buswatch.domain.usecase.stop.FavoriteStopUseCase
import com.madonnaapps.buswatch.domain.usecase.stop.RefreshStopsUseCase
import com.madonnaapps.buswatch.ui.common.extension.applicationComponent
import com.madonnaapps.buswatch.ui.main.navigation.NavigationCoordinator
import com.madonnaapps.buswatch.ui.main.navigation.NavigationDescription
import com.madonnaapps.buswatch.ui.main.navigation.NavigationDescription.*
import com.madonnaapps.buswatch.ui.predictions.PredictionsFragment
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationCoordinator {

    companion object {
        const val TAG = "MainActivity"

        // Fragments
        private const val MAIN_FRAG_TAG = "main_frag"
        private const val PREDICTIONS_FRAG_TAG = "predictions_frag"
    }

    @Inject
    lateinit var refreshStopsUseCase: RefreshStopsUseCase

    @Inject
    lateinit var favoriteStopsUseCase: FavoriteStopUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frag_container_main, MainFragment.newInstance(), MAIN_FRAG_TAG)
                .commit()
            refreshStopsUseCase.execute(RefreshStopsSubscriber())
        }
    }

    override fun onDestroy() {
        refreshStopsUseCase.dispose()
        super.onDestroy()
    }

    // NavigationCoordinator

    override fun navigate(description: NavigationDescription) {
        when (description) {
            is MainFragmentNavigationDescription -> {
            }
            is PredictionsFragmentNavigationDescription ->
                navigationToPredictionsFragment(description)
        }
    }

    private fun navigationToPredictionsFragment(description: PredictionsFragmentNavigationDescription) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frag_container_main,
                PredictionsFragment.newInstance(description.stopId),
                PREDICTIONS_FRAG_TAG
            )
            .addToBackStack("main")
            .commit()
    }

    private inner class RefreshStopsSubscriber : DisposableCompletableObserver() {

        override fun onComplete() {
            Log.d(TAG, "===== Stops finished saving")
        }

        override fun onError(e: Throwable) {
            Log.e(TAG, e.localizedMessage, e)
        }
    }
}