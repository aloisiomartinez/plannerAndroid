package com.example.planner.domain.model

import com.example.planner.domain.utils.createCalendarFromTimeMillis
import com.example.planner.domain.utils.toPlannerActivityDate
import com.example.planner.domain.utils.toPlannerActivityDateTime
import com.example.planner.domain.utils.toPlannerActivityTime


data class PlannerActivity(
    val uuid: String,
    val name: String,
    val datetime: Long,
    val isCompleted: Boolean
) {
    private val datetimeCalendar = createCalendarFromTimeMillis(datetime)

    val dateString: String = datetimeCalendar.toPlannerActivityDate()
    val timeString: String = datetimeCalendar.toPlannerActivityTime()
    val datetimeString: String = datetimeCalendar.toPlannerActivityDateTime()
}