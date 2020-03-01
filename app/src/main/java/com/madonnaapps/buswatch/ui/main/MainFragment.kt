package com.madonnaapps.buswatch.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.madonnaapps.buswatch.R
import com.madonnaapps.buswatch.ui.favorites.FavoritesFragment
import com.madonnaapps.buswatch.ui.main.adapter.MainFragmentPagerAdapter
import com.madonnaapps.buswatch.ui.stopmap.StopMapFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    private val pagerAdapter by lazy {
        val fragments = listOf(StopMapFragment.newInstance(), FavoritesFragment.newInstance())
        MainFragmentPagerAdapter(fragments, childFragmentManager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

}