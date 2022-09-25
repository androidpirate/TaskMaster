package com.example.taskmaster.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.Task
import com.example.taskmaster.repo.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel
@Inject
constructor(
    private val repo: TaskRepository
): ViewModel() {

    private val _incompleteTasks = MutableLiveData<List<Task>>()
    val incompleteTasks: LiveData<List<Task>>
        get() = _incompleteTasks

    private val _completedTasks = MutableLiveData<List<Task>>()
    val completedTasks: LiveData<List<Task>>
        get() = _completedTasks

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>>
    get() = _tasks

    init {
        checkDatabase()
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertTask(task)
            checkDatabase()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateTask(task)
            checkDatabase()
        }
    }

    private fun checkDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            _tasks.postValue(repo.getTasks())
            _incompleteTasks.postValue(repo.getIncompleteTasks())
            _completedTasks.postValue(repo.getCompletedTasks())
        }

    }
}