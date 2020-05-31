package com.brocodes.wedoit.duetasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brocodes.wedoit.model.entity.Task
import com.brocodes.wedoit.model.repository.TaskRepository
import kotlinx.coroutines.launch
import java.util.*

class DueTasksViewModel(private val repository: TaskRepository) : ViewModel(){
    private val today = Calendar.getInstance().timeInMillis

    val allDueTasks = repository.getDueTasks(today)

    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }

    fun completeTask(task: Task) = viewModelScope.launch {
        task.isComplete = true
        repository.updateTask(task)
    }

}