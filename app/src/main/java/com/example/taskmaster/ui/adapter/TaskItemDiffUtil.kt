package com.example.taskmaster.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.taskmaster.data.Task

class TaskItemDiffUtilCallback: DiffUtil.ItemCallback<Task>() {

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.details == newItem.details
    }
}