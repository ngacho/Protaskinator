package com.brocodes.wedoit.completedtasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brocodes.wedoit.model.repository.TaskRepository

class CompletedTasksViewModelFactory(private val repository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CompletedTasksViewModel(repository) as T
    }

}