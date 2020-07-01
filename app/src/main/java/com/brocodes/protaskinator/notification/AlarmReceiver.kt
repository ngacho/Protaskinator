package com.brocodes.protaskinator.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val serializedTask = intent?.getStringExtra(NotificationConstants.SERIALIZED_TASK)
        val task = serializedTask?.let { TaskSerializer.deserializeTask(it) }

        val notificationID = intent?.getLongExtra(NotificationConstants.REQUEST_CODE, 0)
        if (task != null && notificationID != null)
            NotificationHelper.setNotification(context!!, task, notificationID)
    }

}
