package com.github.androidpirate.todolistapp.statistics

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class StatisticsPagerAdapter(
    fm: FragmentManager,
    behavior: Int)
    :FragmentStatePagerAdapter(fm, behavior) {

    private val tabTitles = arrayOf(STATISTICS_TITLE, CHART_TITLE)

    override fun getItem(position: Int): Fragment {
        return if(getPageTitle(position) == STATISTICS_TITLE) {
            StatisticsFragment.newInstance()
        } else {
            ChartFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    companion object {
        private const val PAGE_COUNT = 2
        private const val STATISTICS_TITLE = "Statistics"
        private const val CHART_TITLE = "Chart"
    }
}