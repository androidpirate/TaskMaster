package com.github.androidpirate.todolistapp.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Priority(val label: String) {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high")
}

@Entity(tableName = "tasks")
class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int ?= null,
    var title: String ?= "Title",
    var details: String ?= "Details",
    var complete: Boolean ?= false,
    // Priority level, 1: Lowest - 3: Highest
    var priority: Priority ?= Priority.LOW)