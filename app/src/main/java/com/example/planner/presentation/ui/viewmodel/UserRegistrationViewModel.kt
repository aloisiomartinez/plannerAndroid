package com.example.planner.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.data.datasource.AuthenticationLocalDataSource
import com.example.planner.data.datasource.UserRegistrationLocalDataSource
import com.example.planner.core.di.MainServiceLocator
import com.example.planner.domain.model.Profile
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


private val mockToken = """ 
    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
    .eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0
    .KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
""".trimIndent()

class UserRegistrationViewModel: ViewModel() {

    private val userRegistrationLocalDataSource : UserRegistrationLocalDataSource by lazy {
        MainServiceLocator.userRegistrationLocalDataSource
    }

    private val authenticationLocalDataSource: AuthenticationLocalDataSource by lazy {
        MainServiceLocator.authenticationLocalDataSource
    }

    private val _isProfileValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isProfileValid: StateFlow<Boolean> = _isProfileValid.asStateFlow()

    private val _profile: MutableStateFlow<Profile> = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> = _profile.asStateFlow()

    private val _isTokenValid: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isTokenValid: StateFlow<Boolean?> = _isTokenValid.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                userRegistrationLocalDataSource.profile.collect { profile ->
                    _profile.value = profile
                }
            }
            launch {
                while (true) {
                    val tokenExpirationDateTime = authenticationLocalDataSource.expirationDatetime.firstOrNull()
                    tokenExpirationDateTime?.let { tokenExpirationDateTime ->
                        val datetimeNow = System.currentTimeMillis()
                        Log.d("CheckIsTokenValid", "viewModelScope: isTokenValid = ${isTokenValid.value}")
                        _isTokenValid.value = tokenExpirationDateTime > datetimeNow
                    }
                    delay(5_000)
                }
            }

        }
    }

    fun updateProfile(
        name: String? = null,
        email: String? = null,
        phone: String? = null,
        image: String?= null
    ) {
        if (name == null && email == null && phone == null && image == null) return

        _profile.update { currentProfile ->
            val updatedProfile = currentProfile.copy(
                name = name ?: currentProfile.name,
                email = email ?: currentProfile.email,
                phone = phone ?: currentProfile.phone,
                image = image ?: currentProfile.image,
            )

            _isProfileValid.update {
                updatedProfile.isValid()
            }
            updatedProfile
        }
    }

    fun getIsUserRegistered() : Boolean {
        return userRegistrationLocalDataSource.getIsUserRegistered()
    }


    fun saveProfile(onCompleted: () -> Unit) {
        viewModelScope.launch {
            async {
                userRegistrationLocalDataSource.saveProfile(profile = profile.value)
                userRegistrationLocalDataSource.saveIsUserRegistered(isUserRegistered = true)
                authenticationLocalDataSource.insertToken(token = mockToken)
                _isTokenValid.value = true
            }.await()
            onCompleted()
        }
    }

    fun obtainNewToken() {
        viewModelScope.launch {
            authenticationLocalDataSource.insertToken(token = mockToken)
            _isTokenValid.value = true
        }
    }

}