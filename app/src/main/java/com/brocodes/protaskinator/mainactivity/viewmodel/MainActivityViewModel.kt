package com.brocodes.protaskinator.mainactivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brocodes.protaskinator.model.entity.Task
import com.brocodes.protaskinator.model.repository.TaskRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
        repository.deleteTask(task)
    }

    fun deleteAllTasks() = viewModelScope.launch {
        repository.deleteAllTasks()
    }

}