package com.brocodes.wedoit.notification

import com.brocodes.wedoit.model.entity.Task
import com.google.gson.Gson

object Serializer {
    private val gson = Gson()

    fun serializeTask(task: Task) : String = gson.toJson(task)

    fun deserializeTask(taskInStringFormat: String): Task = gson.fromJson(taskInStringFormat, Task::class.java)

}
