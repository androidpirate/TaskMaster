package com.github.androidpirate.todolistapp.active

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.androidpirate.todolistapp.data.TaskEntity
import com.github.androidpirate.todolistapp.data.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ActiveTasksViewModel(private val repo: TaskRepository): ViewModel() {

    val tasks: LiveData<List<TaskEntity>> = repo.activeTasks
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getActiveTasks(): LiveData<List<TaskEntity>> {
        return tasks
    }

    fun checkTaskComplete(task: TaskEntity, isChecked: Boolean) = ioScope.launch {
        if(isChecked){
            task.complete = isChecked
        }
        repo.addTask(task)
    }
}