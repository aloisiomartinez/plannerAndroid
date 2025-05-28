package com.example.planner.data.di

import android.app.Application
import com.example.planner.data.datasource.UserRegistrationLocalDataImpl
import com.example.planner.data.datasource.UserRegistrationLocalDataSource

object MainServiceLocator {

    private var _application: Application? = null
    private val application: Application get() = _application!!

    val userRegistrationLocalDataSource: UserRegistrationLocalDataSource by lazy {
        UserRegistrationLocalDataImpl(applicationContext = application.applicationContext)
    }

    fun initialize(application: Application) {
        _application = application
    }

    fun clear() {
        _application = null
    }
}