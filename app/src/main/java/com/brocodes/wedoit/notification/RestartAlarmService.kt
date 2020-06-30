package com.brocodes.wedoit.notification

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.brocodes.wedoit.MyApplication
import com.brocodes.wedoit.model.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class RestartAlarmService : IntentService(NotificationConstants.RESTART_ALARM_SERVICE) {

    @Inject
    lateinit var taskRepository: TaskRepository

    override fun onCreate() {
        val appComponent = (application as MyApplication).appComponent
        appComponent.inject(restartAlarmService = this)
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.i("RestartAlarmService", "Intent well received")
        if (intent != null) {
            val resetAlarmsScope = CoroutineScope(Dispatchers.IO + Job())
            resetAlarmsScope.launch {
                val tasks = taskRepository.getTasksForAlarms()
                tasks.let {
                    AlarmScheduler.resetAlarms(applicationContext, it)
                }
            }
        }

    }
}