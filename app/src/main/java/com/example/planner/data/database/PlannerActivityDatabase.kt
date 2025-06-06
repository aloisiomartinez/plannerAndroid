package com.example.planner.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

const val PLANNER_ACTIVITY_DATABASE_NAME = "planner_activity_database"

@Database(entities = [PlannerActivityEntity::class], version = 1, exportSchema = true)
abstract class PlannerActivityDatabase : RoomDatabase() {
    abstract fun plannerActivityDao(): PlannerActivityDao
}