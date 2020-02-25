package com.github.androidpirate.todolistapp.active


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.androidpirate.todolistapp.R
import com.github.androidpirate.todolistapp.data.TaskEntity
import com.github.androidpirate.todolistapp.detail.TaskDetailFragment
import com.github.androidpirate.todolistapp.util.TaskViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.IllegalStateException

/**
 * A simple [Fragment] subclass.
 */
class ActiveTasksFragment : Fragment(), ActiveTasksAdapter.ActiveTasksAdapterInteractor {
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyListMessage: TextView
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: ActiveTasksAdapter
    private lateinit var viewModel: ActiveTasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_active_tasks, container, false)
        // Setup views
        setupViews(view)
        // Setup view model and view model factory
        setupViewModel()
        // Setup RecyclerView
        setupRecyclerView()
        // Setup FAB
        setupFab()
        return view
    }

    private fun setupViews(view: View) {
        recyclerView = view.findViewById(R.id.rv_tasks)
        emptyListMessage = view.findViewById(R.id.tv_empty_tasks_message)
        fab = view.findViewById(R.id.fab_add)
    }

    private fun setupViewModel() {
        val factory = TaskViewModelFactory(activity?.application!!, null)
        viewModel = ViewModelProvider(this, factory).get(ActiveTasksViewModel::class.java)
    }

    private fun setupRecyclerView() {
        viewModel.getActiveTasks().observe(viewLifecycleOwner, Observer { tasks ->
            adapter = ActiveTasksAdapter(this)
            if(tasks.isEmpty()) {
                displayEmptyTextMessage()
            } else {
                if(recyclerView.visibility == View.GONE)
                    displayRecyclerView()
                adapter.submitList(tasks)
                recyclerView.adapter = adapter
            }
        })
    }

    private fun setupFab() {
        fab.setOnClickListener { navigateToAddTask() }
    }

    private fun displayEmptyTextMessage() {
        recyclerView.visibility = View.GONE
        emptyListMessage.visibility = View.VISIBLE
    }

    private fun displayRecyclerView() {
        recyclerView.visibility = View.VISIBLE
        emptyListMessage.visibility = View.GONE
    }

    private fun navigateToDetails(item: TaskEntity) {
        val args: Bundle
        when (val id = item.id) {
            null -> throw IllegalStateException(MISSING_ID_EXCEPTION_MESSAGE)
            else -> args = TaskDetailFragment.buildArgs(id)
        }
        findNavController().navigate(R.id.active_tasks_to_task_details, args)
    }

    private fun navigateToAddTask() {
        findNavController().navigate(R.id.active_tasks_to_create_task)
    }

    /**
     * ActiveTasksAdapterInteractor Implementation
     */
    override fun onClickTask(item: TaskEntity) {
        navigateToDetails(item)
    }

    override fun onTaskCompleteChecked(task: TaskEntity, isChecked: Boolean) {
        viewModel.checkTaskComplete(task, isChecked)
    }

    companion object {
        private const val MISSING_ID_EXCEPTION_MESSAGE = "No id for task."
    }
}
