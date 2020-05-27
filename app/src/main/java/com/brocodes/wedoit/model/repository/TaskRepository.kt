package com.brocodes.wedoit.model.repository

import android.content.Context
import com.brocodes.wedoit.model.database.DataBaseBuilder
import com.brocodes.wedoit.model.entity.Task

class TaskRepository(context: Context) {
    private val taskDao = DataBaseBuilder.getInstance(context).taskDao()

    fun getIncompleteTasks(isComplete : Boolean)  = taskDao.fetchIncompleteTasks(isComplete)
    fun getPriorityTasks(priority : Int) = taskDao.fetchPriorityTasks(priority)
    suspend fun updateTask(task: Task) = taskDao.update(task)
    suspend fun insertTask(task: Task) = taskDao.insert(task)
    suspend fun deleteTask(task: Task) = taskDao.delete(task)
    suspend fun deleteAllTasks() = taskDao.nukeTable()


}