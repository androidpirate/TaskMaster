package com.github.androidpirate.todolistapp.completed

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.androidpirate.todolistapp.R
import com.github.androidpirate.todolistapp.data.Priority
import com.github.androidpirate.todolistapp.data.TaskEntity
import com.github.androidpirate.todolistapp.util.TaskItemDiffCallback

class CompletedTasksAdapter(private val interactor: CompleteTasksAdapterInteractor)
    : ListAdapter<TaskEntity, CompletedTasksAdapter.TaskHolder>(TaskItemDiffCallback()) {

    /**
     * Interactor interface for CompletedTasksAdapter
     */
    interface CompleteTasksAdapterInteractor {
        fun onLongTaskClick(task: TaskEntity, isSelected: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskHolder(layoutInflater.inflate(
                R.layout.completed_tasks_list_item_layout,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bindItem(getItem(position), interactor)
    }

    class TaskHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context
        private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        private val title: TextView = itemView.findViewById(R.id.tv_list_item_title)
        private val details: TextView = itemView.findViewById(R.id.tv_list_item_details)
        private val cardView: CardView = itemView.findViewById(R.id.cv_list_item_card)
        private var isSelected = false

        fun bindItem(task: TaskEntity, interactor: CompleteTasksAdapterInteractor) {
            title.text = task.title
            details.text = task.details
            itemView.setOnLongClickListener {
                highlightCard(task)
                interactor.onLongTaskClick(task, isSelected)
                true }
            setStrikeThroughOnText()
            setCardBackground(task)
        }

        private fun setStrikeThroughOnText() {
            title.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            details.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        private fun highlightCard(task: TaskEntity) {
            isSelected =
                if(!isSelected) {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(context, R.color.listItemHighLightColor))
                    true
                } else {
                    setCardBackground(task)
                    false
                 }
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