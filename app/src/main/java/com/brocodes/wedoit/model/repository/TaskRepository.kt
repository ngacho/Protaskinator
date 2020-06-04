package com.brocodes.wedoit.model.repository

import com.brocodes.wedoit.model.dao.TaskDao
import com.brocodes.wedoit.model.entity.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    fun getIncompleteTasks() = taskDao.fetchIncompleteTasks()
    fun getCompleteTasks() = taskDao.fetchCompleteTasks()
    fun getPriorityTasks() = taskDao.fetchPriorityTasks()
    suspend fun updateTask(task: Task) = taskDao.update(task)
    suspend fun insertTask(task: Task) = taskDao.insert(task)
    suspend fun deleteTask(task: Task) = taskDao.delete(task)
    suspend fun deleteAllTasks() = taskDao.nukeTable()


}