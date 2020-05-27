package com.brocodes.wedoit.prioritytasks.viewmodel

import androidx.lifecycle.ViewModel
import com.brocodes.wedoit.model.repository.TaskRepository

class PriorityTasksViewModel(repository: TaskRepository) : ViewModel() {
    val priorityTasks = repository.getPriorityTasks(3)
}