package com.brocodes.wedoit.addtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brocodes.wedoit.model.repository.TaskRepository

class AddTaskViewModelFactory constructor(
    private val repository: TaskRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddTaskViewModel(
            repository
        ) as T
    }
}