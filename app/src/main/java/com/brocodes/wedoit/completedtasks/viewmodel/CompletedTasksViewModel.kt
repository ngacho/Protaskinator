package com.brocodes.wedoit.completedtasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brocodes.wedoit.model.entity.Task
import com.brocodes.wedoit.model.repository.TaskRepository
import kotlinx.coroutines.launch

class CompletedTasksViewModel(private val repository: TaskRepository) : ViewModel() {

    val completeTasks = repository.getCompleteTasks()

    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }

    fun inCompleteTask(task: Task) = viewModelScope.launch {
        task.isComplete = false
        repository.updateTask(task)
    }
}