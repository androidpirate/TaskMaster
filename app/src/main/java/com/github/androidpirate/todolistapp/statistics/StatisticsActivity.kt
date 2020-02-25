package com.github.androidpirate.todolistapp.statistics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.github.androidpirate.todolistapp.R
import com.google.android.material.tabs.TabLayout

class StatisticsActivity : AppCompatActivity() {
    private lateinit var pager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        // Setup toolbar
        setupToolbar()
        // Setup views
        setupViews()
        // Setup ViewPager
        setupViewPager()
        // Setup TabLayout with ViewPager
        tabs.setupWithViewPager(pager)
    }

    private fun setupViews() {
        pager = findViewById(R.id.viewpager)
        tabs = findViewById(R.id.sliding_tabs)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewPager() {
        val pagerAdapter = StatisticsPagerAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
            pager.adapter = pagerAdapter
    }
}