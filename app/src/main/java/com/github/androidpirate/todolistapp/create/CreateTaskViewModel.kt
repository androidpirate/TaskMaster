package com.github.androidpirate.todolistapp.create

import androidx.lifecycle.ViewModel
import com.github.androidpirate.todolistapp.data.TaskEntity
import com.github.androidpirate.todolistapp.data.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CreateTaskViewModel(private val repo: TaskRepository) : ViewModel() {

    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun saveTaskToDatabase(task: TaskEntity) = ioScope.launch {
        repo.addTask(task)
    }
}