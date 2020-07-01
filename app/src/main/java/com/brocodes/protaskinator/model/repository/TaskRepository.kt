package com.brocodes.protaskinator.model.repository

import com.brocodes.protaskinator.model.dao.TaskDao
import com.brocodes.protaskinator.model.entity.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    fun getIncompleteTasks() = taskDao.fetchIncompleteTasks()
    fun getCompleteTasks() = taskDao.fetchCompleteTasks()
    fun getPriorityTasks() = taskDao.fetchPriorityTasks()
    suspend fun getTasksForAlarms() = taskDao.fetchTasksForAlarms()
    suspend fun updateTask(task: Task) = taskDao.update(task)
    suspend fun insertTask(task: Task): Long = taskDao.insert(task)
    suspend fun deleteTask(task: Task) = taskDao.delete(task)
    suspend fun deleteAllTasks() = taskDao.nukeTable()


}