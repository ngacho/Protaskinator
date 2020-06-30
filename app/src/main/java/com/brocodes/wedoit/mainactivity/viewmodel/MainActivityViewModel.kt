package com.brocodes.wedoit.mainactivity.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.brocodes.wedoit.model.entity.Task
import com.brocodes.wedoit.model.repository.TaskRepository
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val repository: TaskRepository) :
    ViewModel() {

    val incompleteTasks = repository.getIncompleteTasks()

    val priorityTasks = repository.getPriorityTasks()

    val completeTasks = repository.getCompleteTasks()

    fun addTask(task: Task) : Long = runBlocking {
        repository.insertTask(task)
    }

    fun completeTask(task: Task) = viewModelScope.launch {
        task.isComplete = true
        repository.updateTask(task)
    }

    fun editTask(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }

    fun inCompleteTask(task: Task) = viewModelScope.launch {
        task.isComplete = false
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