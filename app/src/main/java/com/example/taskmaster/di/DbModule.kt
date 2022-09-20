package com.example.taskmaster.di

import android.content.Context
import com.example.taskmaster.data.TaskDao
import com.example.taskmaster.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideTaskDao(@ApplicationContext appContext: Context): TaskDao {
        return TaskDatabase.invoke(appContext).taskDao()
    }
}