package com.example.planner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.core.di.MainServiceLocator
import com.example.planner.data.datasource.PlannerActivityLocalDataSource
import com.example.planner.domain.model.PlannerActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class PlannerActivityViewModel : ViewModel() {

    val plannerActivityLocalDataSource: PlannerActivityLocalDataSource by lazy {
        MainServiceLocator.plannerActivityLocalDataSource
    }

    private val _activities: MutableStateFlow<List<PlannerActivity>> = MutableStateFlow(emptyList())
    val activities: StateFlow<List<PlannerActivity>> = _activities.asStateFlow()

    init {
        viewModelScope.launch {
            plannerActivityLocalDataSource.plannerActivities.collect { activities ->
                _activities.value = activities
            }
        }
    }

    fun insertPlannerActivity(name: String, datetime: Long) {
        val plannerActivity = PlannerActivity(
            uuid = UUID.randomUUID().toString(),
            name = name,
            datetime = datetime,
            isCompleted = false
        )
        plannerActivityLocalDataSource.insert(plannerActivity = plannerActivity)
    }

    fun update(updatedPlannerActivity: PlannerActivity) {
        plannerActivityLocalDataSource.update(plannerActivity = updatedPlannerActivity)
    }

    fun updateIsCompleted(uuid: String, isCompleted: Boolean) {
        plannerActivityLocalDataSource.updateIsCompletedByUuid(uuid = uuid, isCompleted = isCompleted)
    }

    fun delete(uuid: String) {
        plannerActivityLocalDataSource.deleteByUuid(uuid = uuid)
    }

}