package com.brocodes.wedoit.edittask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brocodes.wedoit.model.entity.Task
import com.brocodes.wedoit.model.repository.TaskRepository
import kotlinx.coroutines.launch

class EditTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    fun editTask(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task)
    }
}