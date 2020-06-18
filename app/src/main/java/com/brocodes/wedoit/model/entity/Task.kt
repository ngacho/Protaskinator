package com.brocodes.wedoit.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val taskTitle: String,
    @ColumnInfo(name = "description") val taskDescription: String,
    @ColumnInfo(name = "priority") val priority: Int,
    @ColumnInfo(name = "dueDate") val date: Long,
    @ColumnInfo(name = "complete") var isComplete: Boolean = false,
    @ColumnInfo(name = "workManagerId") var workRequestId : String? = null
) : Serializable
