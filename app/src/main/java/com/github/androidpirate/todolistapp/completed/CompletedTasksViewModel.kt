package com.github.androidpirate.todolistapp.completed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.androidpirate.todolistapp.data.TaskEntity
import com.github.androidpirate.todolistapp.data.TaskRepository
import com.github.androidpirate.todolistapp.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CompletedTasksViewModel(private val repo: TaskRepository) : ViewModel() {
    private val completedTasks = repo.completedTasks
    private var selectedTasks = arrayListOf<TaskEntity>()
    val toastEvent = MutableLiveData<Event<String>>()
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getCompletedTasks(): LiveData<List<TaskEntity>> {
        return completedTasks
    }

    fun addToSelectedTasks(task: TaskEntity) {
        selectedTasks.add(task)
    }

    fun removeFromSelectedTasks(task: TaskEntity) {
        selectedTasks.remove(task)
    }

    fun deleteSelectedTasks() = ioScope.launch {
        if(selectedTasks.isEmpty()) {
           toastEvent.postValue(Event(EMPTY_SELECTED_TASKS_CONTENT))
        } else {
            repo.deleteTasks(selectedTasks)
        }
    }

    fun setSelectedTasksActive() = ioScope.launch {
        if(selectedTasks.isEmpty()) {
            toastEvent.postValue(Event(EMPTY_SELECTED_TASKS_CONTENT))
        } else {
            for (task in selectedTasks) {
                task.complete = false
                repo.addTask(task)
                toastEvent.postValue(Event(TASKS_ACTIVATED_CONTENT))
            }
        }
    }

    companion object {
        private const val EMPTY_SELECTED_TASKS_CONTENT = "No tasks selected. " +
                "Long click to highlight messages from the list first."
        private const val TASKS_ACTIVATED_CONTENT = "Selected tasks are activated."
    }
}