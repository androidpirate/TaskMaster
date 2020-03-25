package com.github.androidpirate.todolistapp.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.androidpirate.todolistapp.TaskMasterApplication
import com.github.androidpirate.todolistapp.active.ActiveTasksViewModel
import com.github.androidpirate.todolistapp.completed.CompletedTasksViewModel
import com.github.androidpirate.todolistapp.create.CreateTaskViewModel
import com.github.androidpirate.todolistapp.data.TaskRepository
import com.github.androidpirate.todolistapp.detail.TaskDetailViewModel
import com.github.androidpirate.todolistapp.statistics.StatisticsViewModel
import java.lang.IllegalArgumentException

class TaskViewModelFactory(
    application: TaskMasterApplication,
    private val taskId: Int ?= null)
    : ViewModelProvider.Factory {

    private val repo = application.taskRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ActiveTasksViewModel::class.java) -> {
                ActiveTasksViewModel(repo) as T
            }
            modelClass.isAssignableFrom(CompletedTasksViewModel::class.java) -> {
                CompletedTasksViewModel(repo) as T
            }
            modelClass.isAssignableFrom(CreateTaskViewModel::class.java) -> {
                CreateTaskViewModel(repo) as T
            }
            modelClass.isAssignableFrom(TaskDetailViewModel::class.java) -> {
                taskId?.let { TaskDetailViewModel(repo, it) } as T
            }
            modelClass.isAssignableFrom(StatisticsViewModel::class.java) -> {
                StatisticsViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}