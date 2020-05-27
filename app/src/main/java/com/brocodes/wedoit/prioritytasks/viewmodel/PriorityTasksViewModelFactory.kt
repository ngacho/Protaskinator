package com.brocodes.wedoit.prioritytasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brocodes.wedoit.model.repository.TaskRepository

class PriorityTasksViewModelFactory(private val repository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PriorityTasksViewModel(repository) as T
    }
}