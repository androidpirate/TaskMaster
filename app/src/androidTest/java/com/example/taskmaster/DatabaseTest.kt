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

//    @Mock
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

    @Test
    fun test_InsertIncompleteTask() {
        dao.insertTask(incompleteTask)
        val databaseTask = dao.getIncompleteTasks()
        assert(databaseTask[0] == incompleteTask)
    }
}