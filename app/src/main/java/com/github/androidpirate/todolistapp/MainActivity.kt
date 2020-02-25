package com.github.androidpirate.todolistapp

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var navController: NavController
    private lateinit var navView: NavigationView
    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get shared preferences
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val darkTheme = sharedPref.getBoolean(getString(R.string.pref_dark_theme_key), false)
        // Set app theme
        setAppTheme(darkTheme)
        setContentView(R.layout.activity_main)
        // Set default shared preferences from resource
        PreferenceManager.setDefaultValues(this, R.xml.app_settings, true)
        // Setup toolbar
        setupToolbar()
        // Setup views
        setupViews()
        // Setup navController
        setupNavController()
        // Set navView listener
        setNavViewListener()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.active_tasks -> { navController.navigate(R.id.active_tasks) }
            R.id.drawer_menu_complete_tasks -> { navController.navigate(R.id.completed_tasks) }
            R.id.drawer_menu_statistics -> { navController.navigate(R.id.statistics) }
            R.id.drawer_menu_settings -> { navController.navigate(R.id.settings) }
        }
        drawer.closeDrawers()
        return true
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = DEFAULT_TOOLBAR_TITLE
        setSupportActionBar(toolbar)
    }

    private fun setupViews() {
        navView = findViewById(R.id.nav_view)
        drawer = findViewById(R.id.drawer)
        navController = findNavController(R.id.nav_host_fragment)
    }

    private fun setupNavController() {
        val appBarConfig = getAppBarConfiguration()
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfig)
    }

    private fun setNavViewListener() {
        navView.setNavigationItemSelectedListener(this)
    }

    private fun getAppBarConfiguration(): AppBarConfiguration {
        return AppBarConfiguration
            .Builder(setOf(R.id.active_tasks))
            .setDrawerLayout(drawer)
            .build()
    }

    private fun updateBackGroundColors() {

    }

    private fun setAppTheme(darkTheme: Boolean) {
        if(darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        private const val DEFAULT_TOOLBAR_TITLE = "Toolbar"
    }
}