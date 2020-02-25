package com.github.androidpirate.todolistapp.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.github.androidpirate.todolistapp.R

class StatisticsView: LinearLayout {
    private lateinit var mainStatsTitle: TextView
    private lateinit var mainStats: TextView
    private lateinit var highStatsTitle: TextView
    private lateinit var highStats: TextView
    private lateinit var mediumStatsTitle: TextView
    private lateinit var mediumStats: TextView
    private lateinit var lowStatsTitle: TextView
    private lateinit var lowStats: TextView

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) :
            super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val view = LayoutInflater.
            from(context)
            .inflate(R.layout.statistics_view, this, true)
        mainStatsTitle = view.findViewById(R.id.tv_main_stats_title)
        mainStats = view.findViewById(R.id.tv_main_stats)
        highStatsTitle = view.findViewById(R.id.tv_high_priority_stats_title)
        highStats = view.findViewById(R.id.tv_high_priority_stats)
        mediumStatsTitle = view.findViewById(R.id.tv_medium_priority_stats_title)
        mediumStats = view.findViewById(R.id.tv_medium_priority_stats)
        lowStatsTitle = view.findViewById(R.id.tv_low_priority_stats_title)
        lowStats = view.findViewById(R.id.tv_low_priority_stats)
    }

    fun setTitles(mainTitle: String, highTitle: String, mediumTitle: String, lowTitle: String) {
        mainStatsTitle.text = mainTitle
        highStatsTitle.text = highTitle
        mediumStatsTitle.text = mediumTitle
        lowStatsTitle.text = lowTitle
    }
}