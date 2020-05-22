package com.brocodes.wedoit.mainactivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brocodes.wedoit.model.entity.Task
import com.brocodes.wedoit.model.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskListViewModel(private val repository: TaskRepository) : ViewModel() {

    val allTasks = viewModelScope.launch {
        repository.getTasksList()
    }

    fun deleteAllTasks() = viewModelScope.launch {
        repository.deleteAllTasks()
    }

    fun deleteTasks(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }


}