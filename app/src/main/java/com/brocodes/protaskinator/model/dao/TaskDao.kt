package com.brocodes.protaskinator.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.brocodes.protaskinator.model.entity.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE complete = :isComplete")
    fun fetchIncompleteTasks(isComplete: Boolean = false): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE complete = :isComplete")
    fun fetchCompleteTasks(isComplete: Boolean = true): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE complete = :isComplete")
    suspend fun fetchTasksForAlarms(isComplete: Boolean = false): List<Task>

    @Insert
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun nukeTable()

    @Query("SELECT * FROM tasks WHERE priority > :priority AND complete = :isComplete")
    fun fetchPriorityTasks(priority: Int = 3, isComplete: Boolean = false): LiveData<List<Task>>
}