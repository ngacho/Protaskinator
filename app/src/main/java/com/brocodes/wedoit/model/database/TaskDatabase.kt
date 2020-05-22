package com.brocodes.wedoit.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brocodes.wedoit.model.dao.TaskDao
import com.brocodes.wedoit.model.entity.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase  : RoomDatabase() {
    abstract fun taskDao() : TaskDao
}