package com.example.taskmaster.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.R
import com.example.taskmaster.data.Task
import com.example.taskmaster.ui.adapter.TaskListItemAdapter
import com.example.taskmaster.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var completedTasksAdapter: TaskListItemAdapter
    private lateinit var incompleteTasksAdapter: TaskListItemAdapter
    private lateinit var rvCompletedTasks: RecyclerView
    private lateinit var rvIncompleteTasks: RecyclerView

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
        rvCompletedTasks = view.findViewById(R.id.rvCompletedTasks)
        rvIncompleteTasks = view.findViewById(R.id.rvIncompleteTasks)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.insertTask(Task(null, "Task1 is greate mothafucka", false))
        viewModel.insertTask(Task(null, "Task2 is greater than mothafucka", false))
        viewModel.insertTask(Task(null, "Task3 is done, mothafucka!", true))

        viewModel.incompleteTasks.observe(viewLifecycleOwner) {
            incompleteTasksAdapter.submitList(it)
            rvCompletedTasks.adapter = completedTasksAdapter
        }

        viewModel.completedTasks.observe(viewLifecycleOwner) {
            completedTasksAdapter.submitList(it)
            rvIncompleteTasks.adapter = incompleteTasksAdapter
        }
    }

    override fun onResume() {
        super.onResume()

    }
}