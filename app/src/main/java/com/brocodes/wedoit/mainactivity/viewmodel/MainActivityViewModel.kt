package com.brocodes.wedoit.mainactivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brocodes.wedoit.model.repository.TaskRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: TaskRepository) : ViewModel() {

    fun deleteAllTasks() = viewModelScope.launch {
        repository.deleteAllTasks()
    }

}