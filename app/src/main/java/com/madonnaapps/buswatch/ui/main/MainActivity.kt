package com.madonnaapps.buswatch.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.madonnaapps.buswatch.R
import com.madonnaapps.buswatch.domain.usecase.stop.FavoriteStopUseCase
import com.madonnaapps.buswatch.domain.usecase.stop.RefreshStopsUseCase
import com.madonnaapps.buswatch.ui.common.extension.applicationComponent
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        private const val MAIN_FRAG_TAG = "main_frag"
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
//            favoriteStopsUseCase.execute(FavoriteStopSubscriber(), FavoriteStopUseCase.Params("E01960"))
//            favoriteStopsUseCase.execute(FavoriteStopSubscriber(), FavoriteStopUseCase.Params("E02810"))
        }
    }

    override fun onDestroy() {
        refreshStopsUseCase.dispose()
        super.onDestroy()
    }

    private inner class RefreshStopsSubscriber : DisposableCompletableObserver() {

        override fun onComplete() {
            Log.d(TAG, "===== Stops finished saving")
        }

        override fun onError(e: Throwable) {
            Log.e(TAG, e.localizedMessage, e)
        }
    }

    private inner class FavoriteStopSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {

        }

        override fun onError(e: Throwable) {

        }
    }
}