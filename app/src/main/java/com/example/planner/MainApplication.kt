package com.example.planner

import android.app.Application
import com.example.planner.data.di.MainServiceLocator

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Acesso ao Application Contexto a partir desse momento
        MainServiceLocator.initialize(application = this)
    }
}