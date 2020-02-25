package com.github.androidpirate.todolistapp.statistics

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

import com.github.androidpirate.todolistapp.data.TaskRepository

class StatisticsViewModel(repo: TaskRepository) : ViewModel() {

    private val activeTasks = repo.activeTasks
    private val completedTasks = repo.completedTasks

    val activeTasksSize = Transformations.map(activeTasks){ it.size }

    val completedTasksSize = Transformations.map(completedTasks){ it.size }

    val activeHighTasksStats = Transformations.map(repo.highPriorityActiveTasks){ it.size }

    val activeMediumTasksStats = Transformations.map(repo.mediumPriorityActiveTasks){ it.size }

    val activeLowTasksStats = Transformations.map(repo.lowPriorityActiveTasks){ it.size }

    val completedHighTasksStats = Transformations.map(repo.highPriorityCompletedTasks){ it.size }

    val completedMediumTasksStats = Transformations.map(repo.mediumPriorityCompletedTasks){ it.size }

    val completedLowTasksStats = Transformations.map(repo.lowPriorityCompletedTasks){ it.size }

}