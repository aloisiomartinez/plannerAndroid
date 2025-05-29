package com.example.planner.data.datasource

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.planner.data.model.Profile
import com.example.planner.data.model.ProfileSerializer
import kotlinx.coroutines.flow.Flow

private const val USER_REGISTRATION_FILE_NAME = "user_registration"
private const val IS_USER_REGISTERED = "is_user_registered"
private const val PROFILE_FILE_NAME = "PROFILE.db"

class UserRegistrationLocalDataImpl(
    private val applicationContext: Context,
) : UserRegistrationLocalDataSource {

    val Context.profileProtoDataStore: DataStore<Profile> by dataStore(
        fileName = PROFILE_FILE_NAME,
        serializer = ProfileSerializer
    )


    val userRegistrationSharedPreferences: SharedPreferences =
            applicationContext.getSharedPreferences(
                USER_REGISTRATION_FILE_NAME,
                Context.MODE_PRIVATE
            )

    override val profile: Flow<Profile>
        get() = applicationContext.profileProtoDataStore.data

    override suspend fun saveProfile(profile: Profile) {
        applicationContext.profileProtoDataStore.updateData {
            profile
        }
    }

    override fun getIsUserRegistered(): Boolean {
        return userRegistrationSharedPreferences.getBoolean(
            IS_USER_REGISTERED,
            false
        )
    }

    override fun saveIsUserRegistered(isUserRegistered: Boolean) {
        with(userRegistrationSharedPreferences.edit()) {
            putBoolean(IS_USER_REGISTERED, isUserRegistered)
            apply()
        }
    }
}