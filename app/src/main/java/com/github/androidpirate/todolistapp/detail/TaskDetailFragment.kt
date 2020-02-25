package com.github.androidpirate.todolistapp.detail


import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.ScrollView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager

import com.github.androidpirate.todolistapp.R
import com.github.androidpirate.todolistapp.data.Priority
import com.github.androidpirate.todolistapp.util.TaskViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class TaskDetailFragment : Fragment() {
    private lateinit var contentContainer: ScrollView
    private lateinit var title: EditText
    private lateinit var details: EditText
    private lateinit var viewModel: TaskDetailViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var taskId: Int? = DEFAULT_TASK_ID
    private var isKeyboardOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        taskId = arguments?.getInt(ARG_TASK_ID, DEFAULT_TASK_ID)
        // Get shared preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_detail, container, false)
        // Set soft keyboard listener
        setSoftKeyboardListener(view)
        // Setup views
        setupViews(view)
        // Setup ViewModel
        setupVieWModel()
        // Setup tasks views
        setupTask()
        // Observe task complete
        observeToastEvent()
        return view
    }

    override fun onPause() {
        super.onPause()
        saveTask()
        if(isKeyboardOn) {
            hideSoftKeyboard()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_set_complete -> {
                viewModel.setTaskComplete()
                findNavController().navigate(R.id.task_details_to_active_tasks)
            }
            R.id.action_set_priority -> {
                showPriorityOptions()
            }
            R.id.action_set_reminder -> {
                // Set a reminder for the task here
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViews(view: View) {
        contentContainer = view.findViewById(R.id.sv_container)
        title = view.findViewById(R.id.et_task_title)
        details = view.findViewById(R.id.et_task_details)
    }

    private fun setupVieWModel() {
        val factory = TaskViewModelFactory(activity?.application!!, taskId)
        viewModel = ViewModelProvider(this, factory).get(TaskDetailViewModel::class.java)
    }

    private fun setupTask() {
        viewModel.getTaskById().observe(viewLifecycleOwner, Observer {
            title.setText(it.title)
            details.setText(it.details)
            setBackgroundColor(it.priority)
            viewModel.setViewModelTask(it)
        })
    }

    private fun saveTask() {
        viewModel.saveTask(title.text.toString(), details.text.toString())
    }

    private fun setBackgroundColor(priority: Priority?) {
        when(priority) {
            Priority.LOW -> contentContainer.setBackgroundColor(
                Color.parseColor(sharedPreferences.getString(getString(R.string.pref_low_task_bg_key), "")))
            Priority.MEDIUM -> contentContainer.setBackgroundColor(
                Color.parseColor(sharedPreferences.getString(getString(R.string.pref_medium_task_bg_key), "")))
            Priority.HIGH -> contentContainer.setBackgroundColor(
                Color.parseColor(sharedPreferences.getString(getString(R.string.pref_high_task_bg_key), "")))
        }
    }

    private fun showPriorityOptions() {
        val priorityLevels = arrayOf(Priority.LOW.toString(), Priority.MEDIUM.toString(), Priority.HIGH.toString())
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(SET_PRIORITY_DIALOG_TITLE)
        dialogBuilder.setItems(priorityLevels) { _, option ->
            when(priorityLevels[option]) {
                Priority.LOW.toString() -> {
                    viewModel.setTaskPriority(Priority.LOW)
                    setBackgroundColor(Priority.LOW) }
                Priority.MEDIUM.toString() -> {
                    viewModel.setTaskPriority(Priority.MEDIUM)
                    setBackgroundColor(Priority.MEDIUM) }
                Priority.HIGH.toString() -> {
                    viewModel.setTaskPriority(Priority.HIGH)
                    setBackgroundColor(Priority.HIGH) }
            }
        }
        .setNegativeButton(SET_PRIORITY_DIALOG_CANCEL_BUTTON_CONTENT) { dialog, _ ->
            dialog.cancel()
        }
        dialogBuilder.show()
    }

    private fun observeToastEvent() {
        viewModel.toastEvent.observe(viewLifecycleOwner, Observer { event ->
            val message = event.getContentIfNoteHandledOrReturnNull()
            if(message != null) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun hideSoftKeyboard() {
        val inputManager: InputMethodManager = activity?.
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val focusedView = activity?.currentFocus
        if(focusedView != null) {
            inputManager
                .hideSoftInputFromWindow(
                    focusedView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun setSoftKeyboardListener(view: View) {
        view.viewTreeObserver
            .addOnGlobalLayoutListener {
                val rect = Rect()
                view.getWindowVisibleDisplayFrame(rect)
                val heightDiff = view.rootView.height - (rect.bottom - rect.top)
                if(heightDiff > 500) {
                    isKeyboardOn = true
                } else if(heightDiff < 500) {
                    isKeyboardOn = false
                }
            }
    }

    companion object {
        private const val ARG_TASK_ID = "task_id"
        private const val DEFAULT_TASK_ID = 0
        private const val SET_PRIORITY_DIALOG_TITLE = "Set Priority"
        private const val SET_PRIORITY_DIALOG_CANCEL_BUTTON_CONTENT = "Cancel"

        fun buildArgs(id: Int) : Bundle {
            val args = Bundle()
            args.putInt(ARG_TASK_ID, id)
            return args
        }
    }
}
