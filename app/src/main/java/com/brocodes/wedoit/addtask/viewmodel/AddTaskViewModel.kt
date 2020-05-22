package com.brocodes.wedoit.addtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brocodes.wedoit.model.entity.Task
import com.brocodes.wedoit.model.repository.TaskRepository
import kotlinx.coroutines.launch

class AddTaskViewModel(private val repository: TaskRepository) : ViewModel(){
    fun addTask(task : Task) = viewModelScope.launch {
        repository.insertTask(task)
    }
}