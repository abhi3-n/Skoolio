package com.project.skoolio.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelProvider @Inject constructor(
    private val loginViewModel: LoginViewModel,
    private val registrationScreenViewModel: RegistrationScreenViewModel,
    private val otpValidationViewModel: OtpValidationViewModel,
    private val pendingApprovalsViewModel: PendingApprovalsViewModel,
    private val attendanceViewModel: AttendanceViewModel
):ViewModel(){

    fun getLoginViewModel() = loginViewModel
    fun getRegistrationScreenViewModel() = registrationScreenViewModel
    fun getOtpValidationViewModel() = otpValidationViewModel
    fun getPendingApprovalsViewModel() = pendingApprovalsViewModel
    fun getAttendanceViewModel() = attendanceViewModel
}