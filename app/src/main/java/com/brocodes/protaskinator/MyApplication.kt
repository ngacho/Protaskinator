package com.brocodes.protaskinator

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.brocodes.protaskinator.di.AppComponent
import com.brocodes.protaskinator.di.DaggerAppComponent
import com.brocodes.protaskinator.notification.NotificationHelper

open class MyApplication : Application() {
    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        //register the notification channel.
        // Now that this channel is created, you can send a notification to it
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT,
            true,
            getString(R.string.app_name),
            "Due Tasks Notification Channel"
        )
        
    }
}