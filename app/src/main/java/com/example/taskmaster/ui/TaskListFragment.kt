package com.example.taskmaster.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.R
import com.example.taskmaster.data.Task
import com.example.taskmaster.ui.adapter.TaskListItemAdapter
import com.example.taskmaster.viewmodel.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var completedTasksAdapter: TaskListItemAdapter
    private lateinit var incompleteTasksAdapter: TaskListItemAdapter
    private lateinit var emptyList: View
    private lateinit var taskList: View
    private lateinit var rvCompletedTasks: RecyclerView
    private lateinit var rvIncompleteTasks: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        completedTasksAdapter = TaskListItemAdapter()
        incompleteTasksAdapter = TaskListItemAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        emptyList = view.findViewById(R.id.empty_list)
        taskList = view.findViewById(R.id.task_list)
        rvCompletedTasks = view.findViewById(R.id.rvCompletedTasks)
        rvIncompleteTasks = view.findViewById(R.id.rvIncompleteTasks)
        fab = view.findViewById(R.id.fab)
        fab.bringToFront()
        fab.setOnClickListener {
            Log.d("Test", "FAB clicked.")
            navigateToCreateTaskFragment()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.insertTask(Task(null, "This is a incomplete task.", false))
        viewModel.insertTask(Task(null, "This is a completed task.", true))

        viewModel.tasks.observe(viewLifecycleOwner) {
            if(it.isEmpty()) {
                displayEmptyList()
            }
        }

        viewModel.incompleteTasks.observe(viewLifecycleOwner) {
            incompleteTasksAdapter.submitList(it)
            rvCompletedTasks.adapter = completedTasksAdapter
        }

        viewModel.completedTasks.observe(viewLifecycleOwner) {
            completedTasksAdapter.submitList(it)
            rvIncompleteTasks.adapter = incompleteTasksAdapter
        }
    }

    private fun displayEmptyList() {
        emptyList.visibility = View.VISIBLE
        taskList.visibility = View.GONE
    }

    private fun navigateToCreateTaskFragment() {
        findNavController().navigate(R.id.action_task_list_to_detail)
    }
}