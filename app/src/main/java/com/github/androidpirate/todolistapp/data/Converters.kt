package com.github.androidpirate.todolistapp.data

import androidx.room.TypeConverter


class Converters {
    @TypeConverter
    fun fromPriorityToInt(value: Priority?): Int? {
        return when (value) {
            null -> null;
            else -> value.ordinal
        }
    }

    @TypeConverter
    fun fromIntToPriority(value: Int?): Priority? {
        return when (value) {
            null -> null
            else -> Priority.values()[value]
        }
    }
}