package com.example.planner.data.datasource

import com.example.planner.domain.model.PlannerActivity
import kotlinx.coroutines.flow.Flow

interface PlannerActivityLocalDataSource {

    val plannerActivities: Flow<List<PlannerActivity>>

    fun getByUuid(uuid: String): PlannerActivity

    fun updateIsCompletedByUuid(uuid: String, isCompleted: Boolean)

    fun update(plannerActivityEntity: PlannerActivity)

    fun deleteByUuid(uuid: String)
}