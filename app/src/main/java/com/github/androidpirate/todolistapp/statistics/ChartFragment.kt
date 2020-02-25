package com.github.androidpirate.todolistapp.statistics


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.androidpirate.todolistapp.R

/**
 * A simple [Fragment] subclass.
 */
class ChartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    companion object {
        fun newInstance() : ChartFragment {
            return ChartFragment()
        }
    }
}
