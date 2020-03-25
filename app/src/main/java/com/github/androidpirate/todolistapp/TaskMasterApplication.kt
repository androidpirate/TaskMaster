package com.github.androidpirate.todolistapp

import android.app.Application
import com.github.androidpirate.todolistapp.data.TaskRepository

class TaskMasterApplication : Application() {
    val taskRepository: TaskRepository
    get() = ServiceLocator.provideTaskRepo(this)
}