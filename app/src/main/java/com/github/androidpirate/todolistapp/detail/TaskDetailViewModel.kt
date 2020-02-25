package com.github.androidpirate.todolistapp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.androidpirate.todolistapp.data.Priority
import com.github.androidpirate.todolistapp.data.TaskEntity
import com.github.androidpirate.todolistapp.data.TaskRepository
import com.github.androidpirate.todolistapp.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TaskDetailViewModel(private val repo: TaskRepository, private var taskId: Int) : ViewModel() {
    private lateinit var task: TaskEntity
    private var isPriorityChanged: Boolean = false
    val toastEvent = MutableLiveData<Event<String>>()
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getTaskById(): LiveData<TaskEntity> {
        return repo.getTaskById(taskId)
    }

    fun saveTask(title: String, details: String) = ioScope.launch {
        if(checkIfTaskHasBeenUpdated(title, details)) {
            val editTask = TaskEntity(taskId, title, details, task.complete, task.priority)
            repo.addTask(editTask)
        }
    }

    fun setViewModelTask(task: TaskEntity) {
        this.task = task
    }

    fun setTaskComplete() {
        task.complete = true
        toastEvent.postValue(Event(TASK_COMPLETED_CONTENT))
    }

    fun setTaskPriority(priority: Priority) {
        task.priority = priority
        toastEvent.postValue(Event(TASK_PRIORITY_CHANGED_CONTENT + priority))
        isPriorityChanged = true
    }

    private fun checkIfTaskHasBeenUpdated(title: String, details: String): Boolean {
        if(task.title != title ||
            task.details != details ||
            task.complete == true ||
            isPriorityChanged) {
            return true
        }
        return false
    }

    companion object {
        private const val TASK_COMPLETED_CONTENT = "Task completed."
        private const val TASK_PRIORITY_CHANGED_CONTENT = "Task priority is changed to "
    }
}