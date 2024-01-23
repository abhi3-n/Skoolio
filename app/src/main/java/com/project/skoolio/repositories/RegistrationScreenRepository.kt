package com.project.skoolio.repositories

import com.project.skoolio.network.Backend
import javax.inject.Inject

class RegistrationScreenRepository @Inject constructor(private val backend: Backend){

    suspend fun sendOTP():Unit{
        backend.sendOTP()
    }
}