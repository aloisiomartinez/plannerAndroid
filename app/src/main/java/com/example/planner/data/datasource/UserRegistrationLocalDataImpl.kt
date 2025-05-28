package com.example.planner.data.datasource

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

private const val USER_REGISTRATION_FILE_NAME = "user_registration"
private const val IS_USER_REGISTERED = "is_user_registered"


class UserRegistrationLocalDataImpl(
    private val applicationContext: Application
) : UserRegistrationLocalDataSource {

    val userRegistrationSharedPreferences: SharedPreferences =
            applicationContext.getSharedPreferences(
                USER_REGISTRATION_FILE_NAME,
                Context.MODE_PRIVATE
            )

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