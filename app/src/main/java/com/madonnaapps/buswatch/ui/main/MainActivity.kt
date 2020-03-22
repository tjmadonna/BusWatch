package com.madonnaapps.buswatch.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.madonnaapps.buswatch.R
import com.madonnaapps.buswatch.domain.usecase.stop.FavoriteStopUseCase
import com.madonnaapps.buswatch.domain.usecase.stop.RefreshStopsUseCase
import com.madonnaapps.buswatch.ui.common.extension.applicationComponent
import com.madonnaapps.buswatch.ui.favorites.FavoritesFragment
import com.madonnaapps.buswatch.ui.main.adapter.MainFragmentPagerAdapter
import com.madonnaapps.buswatch.ui.main.navigation.NavigationCoordinator
import com.madonnaapps.buswatch.ui.main.navigation.NavigationDescription
import com.madonnaapps.buswatch.ui.main.navigation.NavigationDescription.*
import com.madonnaapps.buswatch.ui.predictions.PredictionsActivity
import com.madonnaapps.buswatch.ui.stopmap.StopMapFragment
import io.reactivex.observers.DisposableCompletableObserver
import kotlinx.android.synthetic.main.activity_main.bottom_nav_main
import kotlinx.android.synthetic.main.activity_main.pager_main
import kotlinx.android.synthetic.main.content_toolbar.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationCoordinator {

    companion object {
        const val TAG = "MainActivity"
    }

    @Inject
    lateinit var refreshStopsUseCase: RefreshStopsUseCase

    @Inject
    lateinit var favoriteStopsUseCase: FavoriteStopUseCase

    private val pagerAdapter by lazy {
        val fragments = listOf(StopMapFragment.newInstance(), FavoritesFragment.newInstance())
        MainFragmentPagerAdapter(fragments, supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        applicationComponent.inject(this)
        setupInitialLoad()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupToolbar()
        setupViews()
    }

    override fun onDestroy() {
        refreshStopsUseCase.dispose()
        super.onDestroy()
    }

    // Setup functions

    private fun setupInitialLoad() {
        refreshStopsUseCase.execute(RefreshStopsSubscriber())
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupViews() {
        pager_main.adapter = pagerAdapter

        bottom_nav_main.setOnNavigationItemSelectedListener { menuItem ->
            return@setOnNavigationItemSelectedListener when (menuItem.itemId) {
                R.id.action_map -> {
                    pager_main.currentItem = 0
                    true
                }
                R.id.action_favorites -> {
                    pager_main.currentItem = 1
                    true
                }
                else -> false
            }
        }
    }

    // NavigationCoordinator

    override fun navigate(description: NavigationDescription) {
        when (description) {
            is PredictionsNavigationDescription -> navigationToPredictionsActivity(description)
        }
    }

    private fun navigationToPredictionsActivity(description: PredictionsNavigationDescription) {
        val intent = PredictionsActivity.createIntent(this, description.stopId)
        ContextCompat.startActivity(this, intent, null)
    }

    private class RefreshStopsSubscriber : DisposableCompletableObserver() {

        override fun onComplete() {
            Log.d(TAG, "===== Stops finished saving")
        }

        override fun onError(e: Throwable) {
            Log.e(TAG, e.localizedMessage, e)
        }
    }
}