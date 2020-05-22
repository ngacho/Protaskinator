package com.brocodes.wedoit.mytasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brocodes.wedoit.mainactivity.viewmodel.MainActivityViewModel
import com.brocodes.wedoit.model.repository.TaskRepository

class MyTasksViewModelFactory(private val taskRepository: TaskRepository) :  ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyTasksViewModel(taskRepository) as T
    }
}