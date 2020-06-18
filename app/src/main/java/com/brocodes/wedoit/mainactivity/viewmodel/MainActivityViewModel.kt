package com.brocodes.wedoit.mainactivity.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.brocodes.wedoit.model.entity.Task
import com.brocodes.wedoit.model.repository.TaskRepository
import com.brocodes.wedoit.notification.NotificationWorker
import com.brocodes.wedoit.notification.Serializer
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val repository: TaskRepository) :
    ViewModel() {

    val incompleteTasks = repository.getIncompleteTasks()

    val priorityTasks = repository.getPriorityTasks()

    val completeTasks = repository.getCompleteTasks()

    fun addTask(task: Task) = viewModelScope.launch {
        val taskInStringFormat = Serializer.serializeTask(task)

        val taskData = Data.Builder()
            .putString("due_task", taskInStringFormat)
            .build()
        //subtract the time for notification and time now to set it to the work request
        val durationBeforeNotification =
            task.date - Calendar.getInstance(Locale.getDefault()).timeInMillis
        //create a work request to create the notification
        val notificationWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(durationBeforeNotification, TimeUnit.MILLISECONDS)
            .setInputData(taskData)
            .build()

        //assign the work request id to be saved in the database
        task.workRequestId = notificationWorkRequest.id.toString()

        WorkManager.getInstance()
            .enqueue(notificationWorkRequest)

        repository.insertTask(task)

    }

    fun completeTask(task: Task) = viewModelScope.launch {
        task.isComplete = true
        task.workRequestId?.let {
            val workId = UUID.fromString(it)
            WorkManager.getInstance().cancelWorkById(workId)
        }
        repository.updateTask(task)
    }

    fun editTask(task: Task) = viewModelScope.launch {
        val workManager = WorkManager.getInstance()
        //cancel the original work request
        task.workRequestId?.let {
            val workId = UUID.fromString(it)
            WorkManager.getInstance().cancelWorkById(workId)
        }
        //set a new request if the date due isnt less than today
        if (task.date > Calendar.getInstance().timeInMillis) {
            val taskInStringFormat = Serializer.serializeTask(task)

            val taskData = Data.Builder()
                .putString("due_task", taskInStringFormat)
                .build()
            //subtract the time for notification and time now to set it to the work request
            val durationBeforeNotification =
                task.date - Calendar.getInstance(Locale.getDefault()).timeInMillis
            //create a work request to create the notification
            val notificationWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(durationBeforeNotification, TimeUnit.MILLISECONDS)
                .setInputData(taskData)
                .build()

            //assign the work request id to be saved in the database
            task.workRequestId = notificationWorkRequest.id.toString()

            WorkManager.getInstance()
                .enqueue(notificationWorkRequest)
        }

        repository.updateTask(task)
    }

    fun inCompleteTask(task: Task) = viewModelScope.launch {
        task.isComplete = false
        //set a new request if the date due isnt less than today
        if (task.date > Calendar.getInstance().timeInMillis) {
            val taskInStringFormat = Serializer.serializeTask(task)

            val taskData = Data.Builder()
                .putString("due_task", taskInStringFormat)
                .build()
            //subtract the time for notification and time now to set it to the work request
            val durationBeforeNotification =
                task.date - Calendar.getInstance(Locale.getDefault()).timeInMillis
            //create a work request to create the notification
            val notificationWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(durationBeforeNotification, TimeUnit.MILLISECONDS)
                .setInputData(taskData)
                .build()

            //assign the work request id to be saved in the database
            task.workRequestId = notificationWorkRequest.id.toString()

            WorkManager.getInstance()
                .enqueue(notificationWorkRequest)
        }
        repository.updateTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        try {
            task.workRequestId?.let {
                val workId = UUID.fromString(it)
                WorkManager.getInstance().cancelWorkById(workId)
            }
        } catch (e: Exception) {
            Log.d("Work not found", "${e.message}")
        }
        repository.deleteTask(task)
    }

    fun deleteAllTasks() = viewModelScope.launch {
        try {
            WorkManager.getInstance().cancelAllWork()
        } catch (e: Exception) {
            Log.d("Work Not found", "${e.message}")
        }
        repository.deleteAllTasks()
    }

}