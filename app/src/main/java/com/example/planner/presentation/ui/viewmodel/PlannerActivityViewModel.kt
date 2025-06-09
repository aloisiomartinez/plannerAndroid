package com.example.planner.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.core.di.MainServiceLocator
import com.example.planner.core.di.MainServiceLocator.ioDispatcher
import com.example.planner.core.di.MainServiceLocator.mainDispatcher
import com.example.planner.data.datasource.PlannerActivityLocalDataSource
import com.example.planner.domain.model.PlannerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID

class PlannerActivityViewModel : ViewModel() {

    val plannerActivityLocalDataSource: PlannerActivityLocalDataSource by lazy {
        MainServiceLocator.plannerActivityLocalDataSource
    }

    private val _activities: MutableStateFlow<List<PlannerActivity>> = MutableStateFlow(emptyList())
    val activities: StateFlow<List<PlannerActivity>> = _activities.asStateFlow()

    fun fetchActivities() {
        viewModelScope.launch {
           launch {
               plannerActivityLocalDataSource.plannerActivities
                   .flowOn(ioDispatcher)
                   .collect { activities ->
                       withContext(mainDispatcher) {
                           _activities.value = activities
                       }

                   }
           }

            launch {
                delay(3_000)
                insert(name = "Jantar", datetime = Calendar.getInstance().timeInMillis)

                delay(3_000)
                insert(name = "Academia", datetime = Calendar.getInstance().timeInMillis)

                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_MONTH, 3)
                insert(name = "Academia", datetime = calendar.timeInMillis)
            }
        }
    }

    fun insert(name: String, datetime: Long) {
        val plannerActivity = PlannerActivity(
            uuid = UUID.randomUUID().toString(),
            name = name,
            datetime = datetime,
            isCompleted = false
        )
        viewModelScope.launch {
            withContext(ioDispatcher) {
                plannerActivityLocalDataSource.insert(plannerActivity = plannerActivity)
            }
        }
    }

    fun update(updatedPlannerActivity: PlannerActivity) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                plannerActivityLocalDataSource.update(plannerActivity = updatedPlannerActivity)
            }
        }
    }

    fun updateIsCompleted(uuid: String, isCompleted: Boolean) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                plannerActivityLocalDataSource.updateIsCompletedByUuid(uuid = uuid, isCompleted = isCompleted)
            }
        }
    }

    fun delete(uuid: String) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                plannerActivityLocalDataSource.deleteByUuid(uuid = uuid)
            }
        }
    }

}