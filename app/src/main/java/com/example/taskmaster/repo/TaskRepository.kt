package com.example.taskmaster.repo

import com.example.taskmaster.data.Task
import com.example.taskmaster.data.TaskDao
import javax.inject.Inject

class TaskRepository
@Inject
constructor(private val dao: TaskDao)
{
    suspend fun getTasks(): List<Task> {
        return dao.getTasks()
    }

    suspend fun getIncompleteTasks(): List<Task> {
        return dao.getIncompleteTasks()
    }

    suspend fun getCompletedTasks(): List<Task> {
        return dao.getCompletedTasks()
    }

    suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        dao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }
}