package com.github.androidpirate.todolistapp.completed


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.androidpirate.todolistapp.R
import com.github.androidpirate.todolistapp.TaskMasterApplication
import com.github.androidpirate.todolistapp.data.TaskEntity
import com.github.androidpirate.todolistapp.util.TaskViewModelFactory
import java.lang.IllegalArgumentException

/**
 * A simple [Fragment] subclass.
 */
class CompletedTasksFragment : Fragment(), CompletedTasksAdapter.CompleteTasksAdapterInteractor {
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyMessage: TextView
    private lateinit var viewModel: CompletedTasksViewModel
    private lateinit var adapter: CompletedTasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_completed_tasks, container, false)
        // Setup views
        setupViews(view)
        // Setup ViewModel
        setupViewModel()
        // Setup recyclerView
        setupRecyclerView()
        // Observe toast events
        observerToastEvent()
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.completed_tasks_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_delete_selected -> { viewModel.deleteSelectedTasks() }
            R.id.action_set_active -> { viewModel.setSelectedTasksActive() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViews(view: View) {
        recyclerView = view.findViewById(R.id.rv_completed_tasks)
        emptyMessage = view.findViewById(R.id.tv_empty_completed_tasks_message)
    }

    private fun setupViewModel() {
        val factory = TaskViewModelFactory(
            requireActivity().application as TaskMasterApplication,
            null)
        viewModel = ViewModelProvider(this, factory).get(CompletedTasksViewModel::class.java)
    }

    private fun setupRecyclerView() {
        viewModel.getCompletedTasks().observe(viewLifecycleOwner, Observer { completedTasks ->
            adapter = CompletedTasksAdapter(this)
            if(completedTasks.isEmpty()) {
                displayEmptyListMessage()
            } else {
                if(recyclerView.visibility == View.GONE) {
                    displayRecyclerView()
                }
                adapter.submitList(completedTasks)
                recyclerView.adapter = adapter
            }
        })
    }

    private fun observerToastEvent() {
        viewModel.toastEvent.observe(viewLifecycleOwner, Observer { event ->
            val message = event.getContentIfNoteHandledOrReturnNull()
            if(message != null) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayEmptyListMessage() {
        recyclerView.visibility = View.GONE
        emptyMessage.visibility = View.VISIBLE
    }

    private fun displayRecyclerView() {
        recyclerView.visibility = View.VISIBLE
        emptyMessage.visibility = View.GONE
    }

    /**
     * CompletedTasksAdapterInteractor Implementation
     */
    override fun onLongTaskClick(task: TaskEntity, isSelected: Boolean) {
        if(isSelected) {
            viewModel.addToSelectedTasks(task)
        } else {
            viewModel.removeFromSelectedTasks(task)
        }
    }

    companion object {
        private const val NULL_EVENT_CONTENT_MESSAGE = "Event content is null."
    }
}
