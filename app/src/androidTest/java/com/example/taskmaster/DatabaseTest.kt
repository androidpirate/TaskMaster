package com.example.taskmaster

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.taskmaster.data.Task
import com.example.taskmaster.data.TaskDao
import com.example.taskmaster.data.TaskDatabase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao: TaskDao

    private lateinit var completedTask: Task
    private lateinit var incompleteTask: Task

    @Before
    fun prepare() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java).build()
        dao = db.taskDao()
        completedTask = Task(0, "This is a task.", true)
        incompleteTask = Task(1, "This is a task.", false)
    }

    // Test Insert
    @Test
    fun should_Return_The_Same_Task_When_An_Incomplete_Task_Is_Inserted_To_Database() {
        dao.insertTask(incompleteTask)
        val databaseTask = dao.getIncompleteTasks()
        assert(databaseTask[0] == incompleteTask)
    }

    @Test
    fun should_Return_The_Same_Task_When_A_Completed_Task_Is_Inserted_To_Database() {
        dao.insertTask(completedTask)
        val databaseTask = dao.getCompletedTasks()
        assert(databaseTask[0] == completedTask)
    }

    // Test Update
    @Test
    fun should_Return_Updated_Data_When_A_Task_Is_Updated_On_Database() {
        var task = Task(0, "This is a task.", false)
        dao.insertTask(task)
        task.details = "This is an updated task."
        task.isCompleted = true
        dao.updateTask(task)
        val updatedTask = dao.getTasks()
        assert(updatedTask[0].id == task.id &&
            updatedTask[0].details == task.details &&
            updatedTask[0].isCompleted == task.isCompleted)
    }

    // Test Delete
    @Test
    fun should_Return_Empty_List_When_Only_Task_In_Database_Is_Deleted() {
        var task = Task(0, "A task to delete.", false)
        dao.insertTask(task)
        dao.deleteTask(task)
        val tasks = dao.getTasks()
        assert(tasks.isEmpty())
    }
}