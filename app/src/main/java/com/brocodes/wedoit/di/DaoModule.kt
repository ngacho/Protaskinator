package com.brocodes.wedoit.di

import android.content.Context
import com.brocodes.wedoit.model.database.DataBaseBuilder
import dagger.Module
import dagger.Provides

@Module
class DaoModule {

    @Provides
    //Function that provides room
    fun provideRoom(context: Context) = DataBaseBuilder.getInstance(context).taskDao()

}