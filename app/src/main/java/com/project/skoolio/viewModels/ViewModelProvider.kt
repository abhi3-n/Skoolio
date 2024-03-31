package com.project.skoolio.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelProvider @Inject constructor(
    private val loginViewModel: LoginViewModel,
    private val registrationScreenViewModel: RegistrationScreenViewModel,
    private val otpValidationViewModel: OtpValidationViewModel,
    private val pendingApprovalsViewModel: PendingApprovalsViewModel,
    private val attendanceViewModel: AttendanceViewModel,
    private val schoolInformationViewModel: SchoolInformationViewModel
):ViewModel(){

    fun getLoginViewModel() = loginViewModel
    fun getRegistrationScreenViewModel() = registrationScreenViewModel
    fun getOtpValidationViewModel() = otpValidationViewModel
    fun getPendingApprovalsViewModel() = pendingApprovalsViewModel
    fun getAttendanceViewModel() = attendanceViewModel
    fun getSchoolInformationViewModel() = schoolInformationViewModel
}