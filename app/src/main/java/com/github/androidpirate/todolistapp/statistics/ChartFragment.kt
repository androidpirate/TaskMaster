package com.github.androidpirate.todolistapp.statistics


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.preference.PreferenceManager

import com.github.androidpirate.todolistapp.R

/**
 * A simple [Fragment] subclass.
 */
class ChartFragment : Fragment() {
    private lateinit var chartImage: ImageView
    private lateinit var sharedPref: SharedPreferences
    private var darkTheme = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        darkTheme = sharedPref.getBoolean(getString(R.string.pref_dark_theme_key), false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chart, container, false)
        // Setup views
        setupViews(view)
        // Setup chart image resource
        setChartImageRes()
        return view
    }

    private fun setupViews(view: View) {
        chartImage = view.findViewById(R.id.iv_chart)
    }

    private fun setChartImageRes() {
        if(darkTheme) {
            chartImage.setImageResource(R.drawable.ic_statistics_white)
        }
    }

    companion object {
        fun newInstance() : ChartFragment {
            return ChartFragment()
        }
    }
}
