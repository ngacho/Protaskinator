package com.brocodes.protaskinator.notification

import com.brocodes.protaskinator.model.entity.Task
import com.google.gson.Gson

object TaskSerializer {
    private val gson = Gson()

    fun serializeTask(task: Task) : String = gson.toJson(task)

    fun deserializeTask(taskInStringFormat: String): Task = gson.fromJson(taskInStringFormat, Task::class.java)

}
