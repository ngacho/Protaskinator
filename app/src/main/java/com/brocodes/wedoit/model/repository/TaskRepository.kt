package com.brocodes.wedoit.model.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.brocodes.wedoit.model.dao.TaskDao
import com.brocodes.wedoit.model.database.DataBaseBuilder
import com.brocodes.wedoit.model.entity.Task

class TaskRepository(context: Context) {
    private val taskDao = DataBaseBuilder.getInstance(context).taskDao()

    fun getTasksList()  = taskDao.fetchAllTasks()
    suspend fun updateTask(task: Task) = taskDao.update(task)
    suspend fun insertTask(task: Task) = taskDao.insert(task)
    suspend fun deleteTask(task: Task) = taskDao.delete(task)
    suspend fun deleteAllTasks() = taskDao.nukeTable()


}