package com.example.taskmaster.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.constraintlayout.widget.Guideline
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.R
import com.example.taskmaster.data.Task
import com.example.taskmaster.ui.adapter.TaskItemClickListener
import com.example.taskmaster.ui.adapter.TaskListItemAdapter
import com.example.taskmaster.viewmodel.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : Fragment(), TaskItemClickListener, MenuProvider {

    private lateinit var viewModel: TaskViewModel
    private lateinit var completedTasksAdapter: TaskListItemAdapter
    private lateinit var incompleteTasksAdapter: TaskListItemAdapter
    private lateinit var emptyList: View
    private lateinit var taskList: View
    private lateinit var rvCompletedTasks: RecyclerView
    private lateinit var rvIncompleteTasks: RecyclerView
    private lateinit var tvCompletedTasksTitle: TextView
    private lateinit var guideLine: Guideline
    private lateinit var fab: FloatingActionButton
    private var isCompletedHidden: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        completedTasksAdapter = TaskListItemAdapter(this)
        incompleteTasksAdapter = TaskListItemAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        emptyList = view.findViewById(R.id.empty_list)
        taskList = view.findViewById(R.id.task_list)
        rvCompletedTasks = view.findViewById(R.id.rvCompletedTasks)
        rvIncompleteTasks = view.findViewById(R.id.rvIncompleteTasks)
        tvCompletedTasksTitle = view.findViewById(R.id.tvCompletedTasksTitle)
        guideLine = view.findViewById(R.id.guideline)
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

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.task_list_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == R.id.hide_completed_tasks) {
            isCompletedHidden = !isCompletedHidden
            if(isCompletedHidden) {
                hideCompletedTasksList()
            } else {
                displayCompletedTasksList()
            }
        }
        return false
    }

    override fun onMenuClosed(menu: Menu) {
        val menuItem = menu.findItem(R.id.hide_completed_tasks)
        if(isCompletedHidden) {
            menuItem.title = "Show Completed"
        } else {
            menuItem.title = "Hide Completed"
        }
    }

    override fun onItemClick(task: Task) {
        task.isCompleted = !task.isCompleted
        viewModel.updateTask(task)
    }

    private fun displayEmptyList() {
        emptyList.visibility = View.VISIBLE
        taskList.visibility = View.GONE
    }

    private fun hideCompletedTasksList() {
        tvCompletedTasksTitle.visibility = View.GONE
        rvCompletedTasks.visibility = View.GONE
        guideLine.setGuidelinePercent(1.0f)
    }

    private fun displayCompletedTasksList() {
        guideLine.setGuidelinePercent(0.5f)
        tvCompletedTasksTitle.visibility = View.VISIBLE
        rvCompletedTasks.visibility = View.VISIBLE
    }

    private fun navigateToCreateTaskFragment() {
        findNavController().navigate(R.id.action_task_list_to_create)
    }
}