package com.project.skoolio.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.skoolio.repositories.RegistrationScreenRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationScreenViewModel @Inject constructor(private val registrationScreenRepository: RegistrationScreenRepository) :ViewModel() {

    fun sendOTP():Unit{
        viewModelScope.launch {
            registrationScreenRepository.sendOTP()
        }
    }
}