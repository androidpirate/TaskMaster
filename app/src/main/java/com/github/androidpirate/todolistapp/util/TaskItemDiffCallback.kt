package com.github.androidpirate.todolistapp.util

import androidx.recyclerview.widget.DiffUtil
import com.github.androidpirate.todolistapp.data.TaskEntity

class TaskItemDiffCallback: DiffUtil.ItemCallback<TaskEntity>() {
    override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        return oldItem.title == newItem.title &&
                oldItem.details == newItem.details
    }
}