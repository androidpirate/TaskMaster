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
}