package com.github.androidpirate.todolistapp.active

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.androidpirate.todolistapp.R
import com.github.androidpirate.todolistapp.data.Priority
import com.github.androidpirate.todolistapp.data.TaskEntity
import com.github.androidpirate.todolistapp.util.TaskItemDiffCallback

class ActiveTasksAdapter(private val interactor: ActiveTasksAdapterInteractor)
    : ListAdapter<TaskEntity, ActiveTasksAdapter.TaskHolder>(TaskItemDiffCallback()) {

    /**
     * Iteractor interface for ActiveTasksAdapter
     */
    interface ActiveTasksAdapterInteractor {
        fun onClickTask(item: TaskEntity)
        fun onTaskCompleteChecked(task: TaskEntity, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TaskHolder(inflater.inflate(
                R.layout.active_tasks_list_item_layout,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bindItem(getItem(position), interactor)
    }

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context
        private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        private val checkBox = itemView.findViewById<CheckBox>(R.id.cb_list_item)
        private val title = itemView.findViewById<TextView>(R.id.tv_list_item_title)
        private val details = itemView.findViewById<TextView>(R.id.tv_list_item_details)
        private val cardView = itemView.findViewById<CardView>(R.id.cv_list_item_card)

        fun bindItem(task: TaskEntity, interactor: ActiveTasksAdapterInteractor) {
            itemView.setOnClickListener {
                interactor.onClickTask(task)
            }
            checkBox.setOnClickListener {
                interactor.onTaskCompleteChecked(task, checkBox.isChecked)
            }
            title.text = task.title
            details.text = task.details
            setCardBackground(task)
        }

        private fun setCardBackground(item: TaskEntity) {
            when(item.priority) {
                Priority.LOW -> cardView.setCardBackgroundColor(
                    Color.parseColor(sharedPreferences.getString(context.getString(R.string.pref_low_task_bg_key), "")))
                Priority.MEDIUM -> cardView.setCardBackgroundColor(
                    Color.parseColor(sharedPreferences.getString(context.getString(R.string.pref_medium_task_bg_key), "")))
                Priority.HIGH -> cardView.setCardBackgroundColor(
                    Color.parseColor(sharedPreferences.getString(context.getString(R.string.pref_high_task_bg_key), "")))
            }
        }
    }
}