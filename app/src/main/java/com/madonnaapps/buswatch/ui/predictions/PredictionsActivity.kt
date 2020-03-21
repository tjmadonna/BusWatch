package com.madonnaapps.buswatch.ui.predictions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.madonnaapps.buswatch.R
import com.madonnaapps.buswatch.ui.common.extension.applicationComponent
import com.madonnaapps.buswatch.ui.predictions.adapter.PredictionsAdapter
import com.madonnaapps.buswatch.ui.predictions.contract.PredictionsState
import com.madonnaapps.buswatch.ui.predictions.contract.StopIntent
import com.madonnaapps.buswatch.ui.predictions.contract.StopState
import kotlinx.android.synthetic.main.activity_predictions.*
import javax.inject.Inject

class PredictionsActivity: AppCompatActivity() {

    companion object {
        fun createIntent(context: Context, stopId: String): Intent {
            val intent = Intent(context, PredictionsActivity::class.java)
            intent.putExtra(STOP_ID_EXTRA_KEY, stopId)
            return intent
        }

        private const val STOP_ID_EXTRA_KEY = "stop_id_extra"
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)
            .get(PredictionsViewModel::class.java)
    }

    private var isStopFavorite = false

    private val adapter = PredictionsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        applicationComponent.inject(this)
        viewModel.injectStopId(intent.extras?.getString(STOP_ID_EXTRA_KEY))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.predictions, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        val menuItem = menu?.findItem(R.id.action_favorite)
        when (isStopFavorite) {
            true -> {
                menuItem?.setIcon(R.drawable.ic_star_black_24dp)
                menuItem?.setTitle(R.string.unfavorite_stop)
            }
            false -> {
                menuItem?.setIcon(R.drawable.ic_star_border_black_24dp)
                menuItem?.setTitle(R.string.favorite_stop)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                viewModel.handleStopIntent(StopIntent.ToggleFavoriteStopIntent)
                return true
            }
        }
        return false
    }

    // Setup Functions

    private fun setupRecyclerView() {
        recycler_predictions.adapter = adapter
        recycler_predictions.layoutManager = LinearLayoutManager(this)
        recycler_predictions.setHasFixedSize(true)
    }

    private fun setupObservers() {
        viewModel.stopState.observe(this, Observer { state ->
            renderStopState(state)
        })

        viewModel.predictionsState.observe(this, Observer { state ->
            when (state) {
                is PredictionsState.LoadingPredictionsState -> renderLoadingPredictionsState()
                is PredictionsState.DataPredictionsState -> renderDataPredictionsState(state)
                is PredictionsState.ErrorPredictionsState -> renderErrorPredictionsState()
            }
        })
    }

    // Render Functions

    private fun renderStopState(state: StopState) {
        title = state.title
        isStopFavorite = state.isFavorite
        invalidateOptionsMenu()
    }

    private fun renderLoadingPredictionsState() {
        if (progress_predictions.visibility == View.GONE) {
            progress_predictions.visibility = View.VISIBLE
            recycler_predictions.visibility = View.GONE
            constraint_empty_predictions.visibility = View.GONE
        }
        adapter.submitPredictions(emptyList(), null)
    }

    private fun renderDataPredictionsState(state: PredictionsState.DataPredictionsState) {
        if (recycler_predictions.visibility == View.GONE) {
            recycler_predictions.visibility = View.VISIBLE
            progress_predictions.visibility = View.GONE
            constraint_empty_predictions.visibility = View.GONE
        }
        adapter.submitPredictions(state.data, null)
    }

    private fun renderErrorPredictionsState() {
        if (constraint_empty_predictions.visibility == View.GONE) {
            constraint_empty_predictions.visibility = View.VISIBLE
            recycler_predictions.visibility = View.GONE
            progress_predictions.visibility = View.GONE
        }
        adapter.submitPredictions(emptyList(), null)
    }
}