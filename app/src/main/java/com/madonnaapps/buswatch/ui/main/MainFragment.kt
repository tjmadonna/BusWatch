package com.madonnaapps.buswatch.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.madonnaapps.buswatch.R
import com.madonnaapps.buswatch.ui.favorites.FavoritesFragment
import com.madonnaapps.buswatch.ui.main.adapter.MainFragmentPagerAdapter
import com.madonnaapps.buswatch.ui.stopmap.StopMapFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance(): MainFragment = MainFragment()
        private const val TITLE_SAVED_STATE_KEY = "title_saved_state"
    }

    private val pagerAdapter by lazy {
        val fragments = listOf(StopMapFragment.newInstance(), FavoritesFragment.newInstance())
        MainFragmentPagerAdapter(fragments, childFragmentManager)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar_main.title = savedInstanceState?.getString(TITLE_SAVED_STATE_KEY)
            ?: activity?.getString(R.string.title_stop_map)

        pager_main.adapter = pagerAdapter

        bottom_nav_main.setOnNavigationItemSelectedListener { menuItem ->
            return@setOnNavigationItemSelectedListener when (menuItem.itemId) {
                R.id.action_map -> {
                    pager_main.currentItem = 0
                    toolbar_main.title = activity?.getString(R.string.title_stop_map)
                    true
                }
                R.id.action_favorites -> {
                    pager_main.currentItem = 1
                    toolbar_main.title = activity?.getString(R.string.title_favorites)
                    true
                }
                else -> false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TITLE_SAVED_STATE_KEY, toolbar_main.title.toString())
    }
}