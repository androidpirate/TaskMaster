package com.github.androidpirate.todolistapp.util

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.androidpirate.todolistapp.create.CreateTaskFragment

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, pendingIntent: Intent?) {
        val notificationHelper = NotificationHelper(context!!,
            pendingIntent?.getStringExtra(CreateTaskFragment.EXTRA_TITLE),
            pendingIntent?.getStringExtra(CreateTaskFragment.EXTRA_DETAILS),
            pendingIntent?.getIntExtra(
                CreateTaskFragment.EXTRA_PRIORITY,
                NotificationHelper.DEFAULT_PRIORITY))

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
                NotificationHelper.NOTIFICATION_ID,
                notificationHelper.getNotificationBuilder().build())
    }


}