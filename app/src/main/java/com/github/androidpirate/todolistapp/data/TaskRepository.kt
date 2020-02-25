package com.github.androidpirate.todolistapp.data

import android.content.Context
import androidx.lifecycle.LiveData

class TaskRepository(context: Context) {

    private val dao: TaskDao = TaskDatabase.getDatabase(context).getDao()
    val activeTasks: LiveData<List<TaskEntity>> = dao.getActiveTasks()
    val completedTasks: LiveData<List<TaskEntity>> = dao.getCompletedTasks()
    val highPriorityActiveTasks: LiveData<List<TaskEntity>> = dao.getHighPriorityActiveTasks()
    val mediumPriorityActiveTasks: LiveData<List<TaskEntity>> = dao.getMediumPriorityActiveTasks()
    val lowPriorityActiveTasks: LiveData<List<TaskEntity>> = dao.getLowPriorityActiveTasks()
    val highPriorityCompletedTasks: LiveData<List<TaskEntity>> = dao.getHighPriorityCompletedTasks()
    val mediumPriorityCompletedTasks: LiveData<List<TaskEntity>> = dao.getMediumPriorityCompletedTasks()
    val lowPriorityCompletedTasks: LiveData<List<TaskEntity>> = dao.getLowPriorityCompletedTasks()

    fun addTask(task: TaskEntity) {
        dao.addTask(task)
    }

    fun getTaskById(id: Int): LiveData<TaskEntity> {
        return dao.getTaskById(id)
    }

    fun deleteTasks(tasks: ArrayList<TaskEntity>) {
        dao.deleteTasks(tasks)
    }

    companion object {
        @Volatile
        var INSTANCE: TaskRepository ?= null

        fun getTaskRepo(context: Context): TaskRepository {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            } else
                @Synchronized {
                    val instance = TaskRepository(context.applicationContext)
                    INSTANCE = instance
                    return instance
                }
        }
    }
}