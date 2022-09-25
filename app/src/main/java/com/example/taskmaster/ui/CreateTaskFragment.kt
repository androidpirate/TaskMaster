package com.example.taskmaster.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskmaster.R
import com.example.taskmaster.data.Task
import com.example.taskmaster.viewmodel.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTaskFragment : Fragment() {

    private lateinit var taskDetails: EditText
    private lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_task, container, false)
        taskDetails = view.findViewById(R.id.etDetails)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_done)
        fab.bringToFront()
        fab.setOnClickListener {
            if(taskDetails.text.isNotEmpty()) {
                viewModel.insertTask(
                    Task(
                        null,
                        taskDetails.text.toString(),
                        false))
            }
            navigateToTaskListFragment()
        }
        return view
    }

    private fun navigateToTaskListFragment() {
        findNavController().navigate(R.id.action_create_to_task_list)
    }
}