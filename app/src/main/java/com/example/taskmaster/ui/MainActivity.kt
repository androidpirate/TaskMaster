package com.example.taskmaster.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmaster.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // TODO: Test Build and Test CI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}