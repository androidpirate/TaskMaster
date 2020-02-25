package com.github.androidpirate.todolistapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE complete == 0 ORDER BY priority DESC")
    fun getActiveTasks(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE complete == 0 AND priority == 3")
    fun getHighPriorityActiveTasks(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE complete == 0 AND priority == 2")
    fun getMediumPriorityActiveTasks(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE complete == 0 AND priority == 1")
    fun getLowPriorityActiveTasks(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE complete == 1 ORDER BY priority DESC")
    fun getCompletedTasks(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE complete == 1 AND priority == 3")
    fun getHighPriorityCompletedTasks(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE complete == 1 AND priority == 2")
    fun getMediumPriorityCompletedTasks(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE complete == 1 AND priority == 1")
    fun getLowPriorityCompletedTasks(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Int): LiveData<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(task: TaskEntity)

    @Delete
    fun deleteTask(task: TaskEntity)

    @Delete
    fun deleteTasks(tasks: ArrayList<TaskEntity>)
}