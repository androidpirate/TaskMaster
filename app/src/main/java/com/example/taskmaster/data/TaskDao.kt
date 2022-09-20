package com.example.taskmaster.data

import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE isCompleted == 1")
    fun getCompletedTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE isCompleted == 0")
    fun getIncompleteTasks(): List<Task>

    @Insert
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
}