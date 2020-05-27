package com.brocodes.wedoit.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.brocodes.wedoit.model.entity.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE complete = :isComplete")
    fun fetchIncompleteTasks(isComplete : Boolean): LiveData<List<Task>>

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun nukeTable()
}