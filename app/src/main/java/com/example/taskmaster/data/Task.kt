package com.example.taskmaster.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var details: String,
    var isCompleted: Boolean
)