package com.example.taskmaster.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.R
import com.example.taskmaster.data.Task

class TaskListItemAdapter (
    private val clickListener: TaskItemClickListener
    ): ListAdapter<Task, TaskListItemAdapter.TaskViewHolder>(TaskItemDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            R.layout.task_item_incomplete ->
                TaskViewHolder(layoutInflater.inflate(R.layout.task_item_incomplete, parent, false))
            R.layout.task_item_completed ->
                TaskViewHolder(layoutInflater.inflate(R.layout.task_item_completed, parent, false))
            else ->
                throw IllegalArgumentException("Unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        getItem(position)?.let { holder.onBindItem(it) }
    }

    override fun getItemViewType(position: Int): Int {
        return if(!getItem(position).isCompleted) {
            R.layout.task_item_incomplete
        } else
            R.layout.task_item_completed
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDetails = itemView.findViewById<TextView>(R.id.tvDetails)
        private val checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)

        fun onBindItem(item: Task) {
            checkBox.setOnClickListener {
                clickListener.onItemClick(item)
            }
            tvDetails.text = item.details
            if(item.isCompleted) {
                tvDetails.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
    }
}