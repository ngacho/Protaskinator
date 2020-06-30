package com.brocodes.wedoit.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.brocodes.wedoit.model.entity.Task
import java.util.*

object AlarmScheduler {

    fun setAlarm(context: Context, task: Task, requestCode: Int) {

        val serializedTask = TaskSerializer.serializeTask(task)
        // get the AlarmManager reference
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        alarmIntent.putExtra(NotificationConstants.SERIALIZED_TASK, serializedTask)
        alarmIntent.putExtra(NotificationConstants.REQUEST_CODE, requestCode)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, alarmIntent, 0)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, task.date, pendingIntent)
    }

    fun resetAlarms(context: Context, tasks: List<Task>) {
        val today = Calendar.getInstance(Locale.getDefault())
        today.set(Calendar.HOUR_OF_DAY, 5)
        tasks.forEach {
            if (it.date > today.timeInMillis) {
                setAlarm(context, it, it.id)
            }
        }
    }


    fun cancelAlarm(context: Context, task: Task) {
        // get the AlarmManager reference
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, task.id, alarmIntent, 0)
        alarmManager.cancel(pendingIntent)
    }


}