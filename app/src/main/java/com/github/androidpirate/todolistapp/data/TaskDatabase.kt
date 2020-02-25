package com.github.androidpirate.todolistapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskEntity::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun getDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase ?= null

        fun getDatabase(context: Context): TaskDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            } else
                @Synchronized {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "tasks_table"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
        }
    }

}