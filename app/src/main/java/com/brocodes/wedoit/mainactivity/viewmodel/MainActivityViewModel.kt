package com.brocodes.wedoit.mainactivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brocodes.wedoit.model.entity.Task
import com.brocodes.wedoit.model.repository.TaskRepository
import kotlinx.coroutines.launch
import java.util.*

class MainActivityViewModel(private val repository: TaskRepository) : ViewModel() {
    private val today = Calendar.getInstance().timeInMillis

    val incompleteTasks = repository.getIncompleteTasks()

    val priorityTasks = repository.getPriorityTasks()

    val completeTasks = repository.getCompleteTasks()

    val allDueTasks = repository.getDueTasks(today)

    fun addTask(task: Task) = viewModelScope.launch {
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
        repository.deleteTask(task)
    }

    fun deleteAllTasks() = viewModelScope.launch {
        repository.deleteAllTasks()
    }

}