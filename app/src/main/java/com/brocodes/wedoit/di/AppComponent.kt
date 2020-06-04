package com.brocodes.wedoit.di

import android.content.Context
import com.brocodes.wedoit.addtask.AddTaskFragment
import com.brocodes.wedoit.mainactivity.MainActivity
import dagger.BindsInstance
import dagger.Component


@Component(modules = [DaoModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    //classes that can be inject by this component
    fun inject(mainActivity: MainActivity)

}