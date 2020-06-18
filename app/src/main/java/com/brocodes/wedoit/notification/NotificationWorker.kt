package com.brocodes.wedoit.notification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        return try {
            val taskInStringFormat = inputData.getString("due_task")
            val dueTask = taskInStringFormat!!.let { Serializer.deserializeTask(it) }
            NotificationHelper.setNotification(context, dueTask)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}