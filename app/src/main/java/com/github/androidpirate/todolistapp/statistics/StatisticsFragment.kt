package com.github.androidpirate.todolistapp.statistics


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.github.androidpirate.todolistapp.R
import com.github.androidpirate.todolistapp.TaskMasterApplication
import com.github.androidpirate.todolistapp.util.StatisticsView
import com.github.androidpirate.todolistapp.util.TaskViewModelFactory
import kotlinx.android.synthetic.main.statistics_view.view.*

/**
 * A simple [Fragment] subclass.
 */
class StatisticsFragment : Fragment() {

    private lateinit var activeStatsView: StatisticsView
    private lateinit var completedStatsView: StatisticsView
    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        // Setup views
        setupViews(view)
        // Setup ViewModel
        setupViewModel()
        // Set statistics
        setStatistics()
        return view
    }

    private fun setupViews(view: View) {
        activeStatsView = view.findViewById(R.id.active_stats_view)
        activeStatsView.setTitles(
            ACTIVE_STATS_MAIN_TITLE,
            HIGH_PRIORITY_STATS_TITLE,
            MEDIUM_PRIORITY_STATS_TITLE,
            LOW_PRIORITY_STATS_TITLE)
        completedStatsView = view.findViewById(R.id.completed_stats_view)
        completedStatsView.setTitles(
            COMPLETED_STATS_MAIN_TITLE,
            HIGH_PRIORITY_STATS_TITLE,
            MEDIUM_PRIORITY_STATS_TITLE,
            LOW_PRIORITY_STATS_TITLE)
    }

    private fun setupViewModel() {
        val factory = TaskViewModelFactory(requireActivity().application as TaskMasterApplication)
        viewModel = ViewModelProvider(this, factory).get(StatisticsViewModel::class.java)
    }

    private fun setStatistics() {
        viewModel.activeTasksSize.observe(viewLifecycleOwner, Observer {
            activeStatsView.tv_main_stats.text = it.toString()
        })
        viewModel.activeHighTasksStats.observe(viewLifecycleOwner, Observer {
            activeStatsView.tv_high_priority_stats.text = it.toString()
        })
        viewModel.activeMediumTasksStats.observe(viewLifecycleOwner, Observer {
            activeStatsView.tv_medium_priority_stats.text = it.toString()
        })
        viewModel.activeLowTasksStats.observe(viewLifecycleOwner, Observer {
            activeStatsView.tv_low_priority_stats.text = it.toString()
        })

        viewModel.completedTasksSize.observe(viewLifecycleOwner, Observer {
            completedStatsView.tv_main_stats.text = it.toString()
        })
        viewModel.completedHighTasksStats.observe(viewLifecycleOwner, Observer {
            completedStatsView.tv_high_priority_stats.text = it.toString()
        })
        viewModel.completedMediumTasksStats.observe(viewLifecycleOwner, Observer {
            completedStatsView.tv_medium_priority_stats.text = it.toString()
        })
        viewModel.completedLowTasksStats.observe(viewLifecycleOwner, Observer {
            completedStatsView.tv_low_priority_stats.text = it.toString()
        })
    }

    companion object {
        private const val ACTIVE_STATS_MAIN_TITLE = "Active Tasks:"
        private const val COMPLETED_STATS_MAIN_TITLE = "Completed Tasks:"
        private const val HIGH_PRIORITY_STATS_TITLE = "High Priority:"
        private const val MEDIUM_PRIORITY_STATS_TITLE = "Medium Priority:"
        private const val LOW_PRIORITY_STATS_TITLE = "Low Priority:"

        fun newInstance() : StatisticsFragment {
            return StatisticsFragment()
        }
    }
}
