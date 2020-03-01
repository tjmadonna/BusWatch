package com.madonnaapps.buswatch.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.madonnaapps.buswatch.R
import com.madonnaapps.buswatch.ui.common.extension.applicationComponent
import com.madonnaapps.buswatch.ui.favorites.adapter.FavoritesAdapter
import com.madonnaapps.buswatch.ui.favorites.contract.FavoritesState.DataFavoritesState
import com.madonnaapps.buswatch.ui.favorites.contract.FavoritesState.EmptyDataFavoritesState
import kotlinx.android.synthetic.main.fragment_favorites.*
import javax.inject.Inject

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    companion object {
        fun newInstance(): FavoritesFragment = FavoritesFragment()
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)
            .get(FavoritesViewModel::class.java)
    }

    private val adapter = FavoritesAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    // Setup Functions

    private fun setupRecyclerView() {
        recycler_favorites.adapter = adapter
        recycler_favorites.layoutManager = LinearLayoutManager(requireContext())
        recycler_favorites.setHasFixedSize(true)
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is DataFavoritesState -> renderDataFavoritesState(state)
                is EmptyDataFavoritesState -> renderEmptyDataFavoritesState(state)
            }
        })
    }

    // Intent Functions

    private fun renderDataFavoritesState(state: DataFavoritesState) {
        if (recycler_favorites.visibility == View.GONE) {
            recycler_favorites.visibility = View.VISIBLE
            constraint_empty_favorites.visibility = View.GONE
        }
        adapter.submitStops(state.data, null)
    }

    private fun renderEmptyDataFavoritesState(state: EmptyDataFavoritesState) {
        if (constraint_empty_favorites.visibility == View.GONE) {
            constraint_empty_favorites.visibility = View.VISIBLE
            recycler_favorites.visibility = View.GONE
        }
        adapter.submitStops(emptyList(), null)
    }
}