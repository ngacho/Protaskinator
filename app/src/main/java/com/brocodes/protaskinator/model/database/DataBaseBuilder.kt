package com.brocodes.protaskinator.model.database

import android.content.Context
import androidx.room.Room

object DataBaseBuilder {

    private var INSTANCE: TaskDatabase? = null

    fun getInstance(context: Context): TaskDatabase {
        if (INSTANCE == null) {
            synchronized(TaskDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java,
            "task_database"
        )
            .fallbackToDestructiveMigration()
            .build()

}