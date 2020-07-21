package com.example.cusnav.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.cusnav.fragment.BicycleFragment
import com.example.cusnav.fragment.HomeFragment

class TabAdapter(fragmentManager: FragmentManager):
FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val tabTitles =
        arrayOf("Bike", "Bicycle")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> BicycleFragment()
            else -> throw IllegalStateException("position $position is invalid for this viewpager")
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}