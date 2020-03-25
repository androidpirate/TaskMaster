package com.github.androidpirate.todolistapp.create


import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.androidpirate.todolistapp.R
import com.github.androidpirate.todolistapp.TaskMasterApplication
import com.github.androidpirate.todolistapp.data.Priority
import com.github.androidpirate.todolistapp.data.TaskEntity
import com.github.androidpirate.todolistapp.util.AlarmReceiver
import com.github.androidpirate.todolistapp.util.TaskViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.Year
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CreateTaskFragment : Fragment() {
    private lateinit var prioritySwitch: RadioGroup
    private lateinit var title: EditText
    private lateinit var details: EditText
    private lateinit var fab: FloatingActionButton
    private lateinit var viewModel: CreateTaskViewModel
    private var priorityLevel: Priority = Priority.LOW
    private var isKeyboardOn: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_task, container, false)
        // Set soft keyboard listener
        setSoftKeyboardListener(view)
        // Set a destination change listener for navigation controller
        setDestinationListener()
        // Setup views
        setupViews(view)
        // Setup view model factory and view model
        setupViewModel()
        // Setup change listener for priority radio group
        setupPrioritySwitch()
        // Setup FAB
        setupFab()
        return view
    }

    override fun onPause() {
        super.onPause()
        if(isKeyboardOn) {
            hideSoftKeyboard()
        }
    }

    private fun setupViews(view: View) {
        prioritySwitch = view.findViewById(R.id.rg_priority_levels)
        title = view.findViewById(R.id.et_task_title)
        details = view.findViewById(R.id.et_task_details)
        fab = view.findViewById(R.id.fab_add_reminder)
    }

    private fun setupViewModel() {
        val factory = TaskViewModelFactory(
            requireActivity().application as TaskMasterApplication,
            null)
        viewModel = ViewModelProvider(this, factory).get(CreateTaskViewModel::class.java)
    }

    private fun setupPrioritySwitch() {
        prioritySwitch.setOnCheckedChangeListener { radioGroup, _ ->
            when(radioGroup.checkedRadioButtonId) {
                R.id.rb_low -> priorityLevel = Priority.LOW
                R.id.rb_medium -> priorityLevel = Priority.MEDIUM
                R.id.rb_high -> priorityLevel = Priority.HIGH
            }
        }
    }

    private fun setupFab() {
        fab.setOnClickListener { addReminder() }
    }

    private fun saveTask() {
        val task = TaskEntity(
            title = title.text.toString(),
            details = details.text.toString(),
            complete = false,
            priority = priorityLevel)
        viewModel.saveTaskToDatabase(task)
    }

    private fun addReminder() {
        val currentDateAndTime = Calendar.getInstance()
        val startYear = currentDateAndTime.get(Calendar.YEAR)
        val startMonth = currentDateAndTime.get(Calendar.MONTH)
        val startDay = currentDateAndTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateAndTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateAndTime.get(Calendar.MINUTE)

        DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener {
                _, year, month, day ->
            TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener {
                    _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                    val c = Calendar.getInstance()
                    c.set(Calendar.YEAR, year)
                    c.set(Calendar.MONTH, month)
                    c.set(Calendar.DAY_OF_MONTH, day)
                    c.set(Calendar.HOUR_OF_DAY, hour)
                    c.set(Calendar.MINUTE, minute)
                    c.set(Calendar.SECOND, 0)
                    setAlarm(c)
            }, startHour, startMinute, true).show()
        }, startYear, startMonth,startDay).show()
    }

    private fun setAlarm(calendar: Calendar) {
        val alarmManager: AlarmManager =
            activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_TITLE, title.text.toString())
        intent.putExtra(EXTRA_DETAILS, details.text.toString())
        when(priorityLevel) {
            Priority.HIGH -> intent.putExtra(EXTRA_PRIORITY, 1)
            Priority.MEDIUM -> intent.putExtra(EXTRA_PRIORITY, 2)
            Priority.LOW -> intent.putExtra(EXTRA_PRIORITY, 3)
        }
        val pendingIntent = PendingIntent
            .getBroadcast(context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun setDestinationListener() {
        findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when(destination.id) {
                    R.id.active_tasks -> saveTask()
            }
        }
    }

    private fun hideSoftKeyboard() {
        val inputManager: InputMethodManager = activity?.
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val focusedView = activity?.currentFocus
        if(focusedView != null) {
            inputManager.hideSoftInputFromWindow(
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
                    fab.hide()
                } else if(heightDiff < 500) {
                    isKeyboardOn = false
                    fab.show()
                }
            }
    }

    companion object {
        private const val ALARM_REQUEST_CODE = 1
        const val EXTRA_TITLE = "extra title"
        const val EXTRA_DETAILS = "extra_details"
        const val EXTRA_PRIORITY = "extra_priority"
    }
}
