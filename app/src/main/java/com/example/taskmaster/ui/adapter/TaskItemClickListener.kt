package com.example.taskmaster.ui.adapter

import com.example.taskmaster.data.Task

interface TaskItemClickListener {

    fun onItemClick(
        task: Task
    )
}