package com.brocodes.protaskinator.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {


        if (intent.action == "android.intent.action.BOOT_COMPLETED"
            ||
            intent.action == "android.intent.action.ACTION_TIME_CHANGED"
        ) {
            val restartAlarmIntent = Intent(context, RestartAlarmService::class.java)
            context.startService(restartAlarmIntent)
        }
    }
}