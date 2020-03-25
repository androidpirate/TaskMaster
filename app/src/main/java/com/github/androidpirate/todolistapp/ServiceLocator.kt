package com.github.androidpirate.todolistapp

import android.content.Context
import com.github.androidpirate.todolistapp.data.TaskRepository

object ServiceLocator {
    @Volatile
    private var taskRepository: TaskRepository ?= null

    fun provideTaskRepo(context: Context) : TaskRepository {
        synchronized(this) {
            return taskRepository ?: createTaskRepo(context)
        }
    }

    private fun createTaskRepo(context: Context): TaskRepository {
        val newRepo = TaskRepository(context)
        taskRepository = newRepo
        return newRepo
    }
}