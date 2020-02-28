package com.github.androidpirate.todolistapp.util

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.preference.PreferenceManager
import com.github.androidpirate.todolistapp.R

class NotificationHelper(val context: Context,
                         private val taskTitle: String?,
                         private val taskDetails: String?,
                         private val taskPriority: Int?): ContextWrapper(context) {
    private lateinit var channel: NotificationChannel
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private var bgColor: String? = DEFAULT_BG_COLOR
    private val notificationManager: NotificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        when(taskPriority) {
            PRIORITY_HIGH -> {
                channel = NotificationChannel(
                    HIGH_CHANNEL_ID,
                    HIGH_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH)
                    .apply { description = HIGH_CHANNEL_DESCRIPTION }
            }
            PRIORITY_MEDIUM -> {
                channel = NotificationChannel(
                    MEDIUM_CHANNEL_ID,
                    MEDIUM_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT)
                    .apply { description = MEDIUM_CHANNEL_DESCRIPTION }
            }
            PRIORITY_HIGH -> {
                channel = NotificationChannel(
                    LOW_CHANNEL_ID,
                    LOW_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW)
                    .apply { description = LOW_CHANNEL_DESCRIPTION }
            }
        }
        notificationManager.createNotificationChannel(channel)
    }

    fun getNotificationBuilder() : NotificationCompat.Builder {
        when(taskPriority) {
            PRIORITY_HIGH -> {
                bgColor = sharedPreferences.getString(
                        getString(R.string.pref_high_task_bg_key),
                        getString(R.string.color_gray_value))
                notificationBuilder = NotificationCompat.Builder(baseContext, HIGH_CHANNEL_ID)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setColor(Color.parseColor(bgColor))
            }
            PRIORITY_MEDIUM -> {
                bgColor = sharedPreferences.getString(
                        getString(R.string.pref_medium_task_bg_key),
                        getString(R.string.color_gray_value))
                notificationBuilder = NotificationCompat.Builder(baseContext, MEDIUM_CHANNEL_ID)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setColor(Color.parseColor(bgColor))
            }
            PRIORITY_LOW -> {
                bgColor = sharedPreferences.getString(
                        getString(R.string.pref_low_task_bg_key),
                        getString(R.string.color_gray_value))
                notificationBuilder = NotificationCompat.Builder(baseContext, LOW_CHANNEL_ID)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setColor(Color.parseColor(bgColor))
            }
        }
        // Create a pending intent that will navigate to ActiveTasksFragment on click
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.active_tasks)
            .createPendingIntent()
        return notificationBuilder
            .setSmallIcon(R.drawable.ic_reminder)
            .setContentTitle(taskTitle)
            .setContentText(taskDetails)
            .setVibrate(longArrayOf(0))
            .setContentIntent(pendingIntent)
    }

    companion object {
        private const val HIGH_CHANNEL_ID = "high_channel"
        private const val HIGH_CHANNEL_NAME = "High Importance Channel"
        private const val HIGH_CHANNEL_DESCRIPTION =
            "This channel is used by high importance level notifications."
        private const val MEDIUM_CHANNEL_ID = "medium_channel"
        private const val MEDIUM_CHANNEL_NAME = "Medium Importance Channel"
        private const val MEDIUM_CHANNEL_DESCRIPTION =
            "This channel is used by medium importance level notifications."
        private const val LOW_CHANNEL_ID = "low_channel"
        private const val LOW_CHANNEL_NAME = "Low Importance Channel"
        private const val LOW_CHANNEL_DESCRIPTION =
            "This channel is used by low importance level notifications."
        private const val DEFAULT_BG_COLOR = ""
        const val NOTIFICATION_ID = 100
        const val DEFAULT_PRIORITY = 2
        private const val PRIORITY_HIGH = 1
        private const val PRIORITY_MEDIUM = 2
        private const val PRIORITY_LOW = 3
    }
}