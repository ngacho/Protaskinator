package com.brocodes.wedoit.mytasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brocodes.wedoit.model.entity.Task
import com.brocodes.wedoit.model.repository.TaskRepository
import kotlinx.coroutines.launch

class MyTasksViewModel(private val repository: TaskRepository) :  ViewModel(){
    val allTasks = repository.getTasksList()

    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }

}