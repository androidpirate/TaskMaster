package com.github.androidpirate.todolistapp.settings


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.github.androidpirate.todolistapp.R

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var darkThemeKey: String
    private lateinit var lowTaskBgKey: String
    private lateinit var mediumTaskBgKey: String
    private lateinit var highTaskBgKey: String
    private lateinit var grayValue : String
    private lateinit var grayLabel : String
    private lateinit var greenValue : String
    private lateinit var greenLabel : String
    private lateinit var yellowValue : String
    private lateinit var yellowLabel : String
    private lateinit var orangeValue : String
    private lateinit var orangeLabel : String
    private lateinit var blueValue : String
    private lateinit var blueLabel : String
    private lateinit var pinkValue : String
    private lateinit var pinkLabel : String

    private lateinit var defaultLowTaskBgColor: String
    private lateinit var defaultMediumTaskBgColor: String
    private lateinit var defaultHighTaskBgColor: String
    private lateinit var sharedPref: SharedPreferences
    private lateinit var themePref: SwitchPreference
    private lateinit var lowTaskBackgroundPref: ListPreference
    private lateinit var mediumTaskBackgroundPref: ListPreference
    private lateinit var highTaskBackgroundPref: ListPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        // Get preferences keys
        getPreferencesKeys()
        // Get color resources
        getColorResources()
        // Set default background colors
        setDefaultBgColors()
        // Setup preference views
        setupPreferenceViews()
        // Set change listeners
        setupChangeListeners()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_settings, rootKey)
    }

    private fun getPreferencesKeys() {
        darkThemeKey = getString(R.string.pref_dark_theme_key)
        lowTaskBgKey = getString(R.string.pref_low_task_bg_key)
        mediumTaskBgKey = getString(R.string.pref_medium_task_bg_key)
        highTaskBgKey = getString(R.string.pref_high_task_bg_key)
    }

    private fun getColorResources() {
        grayValue = getString(R.string.color_gray_value)
        grayLabel = getString(R.string.color_gray_label)
        greenValue = getString(R.string.color_green_value)
        greenLabel = getString(R.string.color_green_label)
        yellowValue = getString(R.string.color_yellow_value)
        yellowLabel = getString(R.string.color_yellow_label)
        orangeValue = getString(R.string.color_orange_value)
        orangeLabel = getString(R.string.color_orange_label)
        blueValue = getString(R.string.color_blue_value)
        blueLabel = getString(R.string.color_blue_label)
        pinkValue = getString(R.string.color_pink_value)
        pinkLabel = getString(R.string.color_pink_label)
    }

    private fun setDefaultBgColors() {
        defaultLowTaskBgColor = grayValue
        defaultMediumTaskBgColor = blueValue
        defaultHighTaskBgColor = yellowValue
    }

    private fun setupPreferenceViews() {
        themePref = findPreference(darkThemeKey)!!
        lowTaskBackgroundPref = findPreference(lowTaskBgKey)!!
        mediumTaskBackgroundPref = findPreference(mediumTaskBgKey)!!
        highTaskBackgroundPref = findPreference(highTaskBgKey)!!
    }

    private fun setupChangeListeners() {
        themePref.setOnPreferenceChangeListener { _, newValue ->
            setSwitchPreferenceValue(darkThemeKey, newValue as Boolean)
            // Recreate activity to apply new theme
            activity?.recreate()
            true
        }
        lowTaskBackgroundPref.summary = getColorLabel(
            sharedPref.getString(lowTaskBgKey, defaultLowTaskBgColor))
        lowTaskBackgroundPref.setOnPreferenceChangeListener { preference, newValue ->
            setListPreferenceValue(lowTaskBgKey, newValue as String)
            preference.summary = getColorLabel(newValue)
            true
        }

        mediumTaskBackgroundPref.summary = getColorLabel(
            sharedPref.getString(mediumTaskBgKey, defaultMediumTaskBgColor))
        mediumTaskBackgroundPref.setOnPreferenceChangeListener { preference, newValue ->
            setListPreferenceValue(mediumTaskBgKey, newValue as String)
            preference.summary = getColorLabel(newValue)
            true
        }

        highTaskBackgroundPref.summary = getColorLabel(
            sharedPref.getString(highTaskBgKey, defaultHighTaskBgColor))
        highTaskBackgroundPref.setOnPreferenceChangeListener { preference, newValue ->
            setListPreferenceValue(highTaskBgKey, newValue as String)
            preference.summary = getColorLabel(newValue)
            true
        }
    }

    private fun setSwitchPreferenceValue(key: String, value: Boolean) {
        sharedPref.edit().putBoolean(key, value).apply()
    }

    private fun setListPreferenceValue(key: String, value: String) {
        sharedPref.edit().putString(key, value).apply()
    }

    private fun getColorLabel(value: String?): String {
        var colorLabel = ""
        when(value) {
            grayValue -> colorLabel =  grayLabel
            greenValue -> colorLabel = greenLabel
            yellowValue-> colorLabel = yellowLabel
            orangeValue -> colorLabel = orangeLabel
            blueValue -> colorLabel = blueLabel
            pinkValue -> colorLabel = pinkLabel
        }
        return colorLabel
    }
}
