package com.example.planner.core.di

import android.app.Application
import androidx.room.Room
import com.example.planner.data.database.PLANNER_ACTIVITY_DATABASE_NAME
import com.example.planner.data.database.PlannerActivityDao
import com.example.planner.data.database.PlannerActivityDatabase
import com.example.planner.data.datasource.AuthenticationLocalDataSource
import com.example.planner.data.datasource.AuthenticationLocalDataSourceImpl
import com.example.planner.data.datasource.PlannerActivityLocalDataSource
import com.example.planner.data.datasource.PlannerActivityLocalDataSourceImpl
import com.example.planner.data.datasource.UserRegistrationLocalDataImpl
import com.example.planner.data.datasource.UserRegistrationLocalDataSource
import kotlinx.coroutines.Dispatchers

object MainServiceLocator {

    private var _application: Application? = null
    private val application: Application get() = _application!!

    val ioDispatcher by lazy {
        Dispatchers.IO
    }

    val mainDispatcher by lazy {
        Dispatchers.Main
    }

    val userRegistrationLocalDataSource: UserRegistrationLocalDataSource by lazy {
        UserRegistrationLocalDataImpl(applicationContext = application.applicationContext)
    }

    val authenticationLocalDataSource: AuthenticationLocalDataSource by lazy {
        AuthenticationLocalDataSourceImpl(applicationContext = application.applicationContext)
    }

    val plannerActivityDao: PlannerActivityDao by lazy {
        val database = Room.databaseBuilder(
            application.applicationContext,
            PlannerActivityDatabase::class.java,
            PLANNER_ACTIVITY_DATABASE_NAME
        ).build()

        database.plannerActivityDao()
    }

    val plannerActivityLocalDataSource: PlannerActivityLocalDataSource by lazy {
        PlannerActivityLocalDataSourceImpl(plannerActivityDao = plannerActivityDao)
    }

    fun initialize(application: Application) {
        _application = application
    }

    fun clear() {
        _application = null
    }
}