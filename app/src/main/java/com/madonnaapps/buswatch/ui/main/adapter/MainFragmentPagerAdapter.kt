package com.madonnaapps.buswatch.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import javax.inject.Inject

class MainFragmentPagerAdapter @Inject constructor(
    private val fragments: List<Fragment>,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
}