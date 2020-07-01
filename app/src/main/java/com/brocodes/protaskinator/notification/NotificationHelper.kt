package com.brocodes.protaskinator.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.brocodes.protaskinator.R
import com.brocodes.protaskinator.mainactivity.MainActivity
import com.brocodes.protaskinator.model.entity.Task

object NotificationHelper {

    private const val GROUP_DUE_TASKS = "com.brocodes.protaskination.DUE_TASKS"

    /**
     * Sets up the notification channels for API 26+.
     * Note: This uses package name + channel name to create unique channelId's.
     *
     * @param context     application context
     * @param importance  importance level for the notificaiton channel
     * @param isBadgeShown   whether the channel should have a notification badge
     * @param name        name for the notification channel
     * @param description description for the notification channel
     */
    fun createNotificationChannel(
        context: Context,
        importance: Int,
        isBadgeShown: Boolean,
        name: String,
        description: String
    ) {
        //safely check the OS version of the app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //create a unique name for the notification channel.
            // The name and description to be displayed in the application's notification settings
            val chanelId = "${context.packageName}-$name"
            Log.i("channel_id", chanelId)
            val channel = NotificationChannel(chanelId, name, importance)
            channel.description = description
            channel.setShowBadge(isBadgeShown)
            channel.enableLights(true)
            channel.lightColor = Color.RED

            //created the channel using the NotificationManager.
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

    }


    fun setNotification(context: Context, task: Task, notificationID: Long) {
        //create a unique channelId for this app using the package name and app name
        val channelId = "${context.packageName}-${context.getString(R.string.app_name)}"
        //use the notificationCompat.Builder to begin building the notification
        val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
            //set small icon to be displayed in the notification Tray. The only required attribute
            setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.mipmap.ic_launcher
                    )
                )

            setContentTitle("An Item from your list is due today")
            setGroup(GROUP_DUE_TASKS)
            setStyle(
                NotificationCompat.BigTextStyle()
                    //set the title for this notification
                    .setBigContentTitle(task.taskTitle)
                    //set the content for the notification
                    .bigText(task.taskDescription)
                    .setSummaryText("Due Item")
            )

            //set the notification to auto cancel when tapped
            setAutoCancel(true)
            //created an intent to launch the main activity
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("due_tasks_fragment", "due_tasks_frag")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            //wrapped intent in a pendingIntent, created through the getActivity method.
            //this returns a description of an activity to be launched
            val pendingIntent =
                PendingIntent.getActivity(context, notificationID.toInt(), intent, 0)
            //called the setContentIntent to attach it to the NotificationCompat.Builder
            setContentIntent(pendingIntent)
        }

        //used the app's context to get a reference to a NotificationManagerCompat.
        val notificationManager = NotificationManagerCompat.from(context)
        //called notify on the notificationManager passing in an identifier and the notification
        notificationManager.notify(notificationID.toInt(), notificationBuilder.build())

    }
}