package com.github.androidpirate.todolistapp.settings

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import com.github.androidpirate.todolistapp.R

class SettingsActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // Get shared preferences
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val darkTheme = sharedPref.getBoolean(getString(R.string.pref_dark_theme_key), false)
        // Set app theme
        setAppTheme(darkTheme)
        setContentView(R.layout.activity_settings)
        // Setup Toolbar
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setAppTheme(darkTheme: Boolean) {
        if(darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
