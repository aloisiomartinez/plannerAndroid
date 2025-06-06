package com.example.planner.data.datasource

import com.example.planner.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface UserRegistrationLocalDataSource {

    val profile: Flow<Profile>
    suspend fun saveProfile(profile: Profile)

    fun getIsUserRegistered(): Boolean

    fun saveIsUserRegistered(isUserRegistered:Boolean)
}