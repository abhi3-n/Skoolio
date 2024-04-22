package com.project.skoolio.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.project.skoolio.model.EmailOtpRequest
import com.project.skoolio.model.Settings.PasswordChangeRequest
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.network.Backend
import com.project.skoolio.utils.BackToLoginScreen
import com.project.skoolio.utils.resetUserDetails
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(private val backend: Backend): ViewModel() {

    val email:MutableState<String> = mutableStateOf("")
    val selectedType:MutableState<String> = mutableStateOf("")

    fun verifyEmailAndReceiveOTP(
        otpValidationViewModel: OtpValidationViewModel,
        navController: NavHostController,
        context: Context,
        progressIndicatorLoading: MutableState<Boolean>
    ) {
        viewModelScope.launch {
            try {
                progressIndicatorLoading.value = true
                if(selectedType.value == "Student"){
                    backend.verifyStudentEmail(email.value)
                }else if(selectedType.value == "Teacher"){
                    backend.verifyTeacherEmail(email.value)
                }else{
                    backend.verifyAdminEmail(email.value)
                }
                Toast.makeText(context,"Email Verified", Toast.LENGTH_SHORT).show()
                val onOtpSuccess:()->Unit = {
                    Toast.makeText(context, "OTP Sent on mail", Toast.LENGTH_SHORT).show()
                    navController.navigate(AppScreens.OtpValidationScreen.name)
                }
                val onOtpFailure:()->Unit = {
                    Toast.makeText(context, "Some error has occured.", Toast.LENGTH_SHORT).show()
                }
                otpValidationViewModel.receiveOTP(EmailOtpRequest(email.value),
                    onOtpSuccess, onOtpFailure, progressIndicatorLoading)
            }
            catch (e:Exception){
                Toast.makeText(context,"Error:${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun changePassword(
        password: String,
        context: Context,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            try {
                if(selectedType.value == "Student"){
                    backend.changeStudentPassword(PasswordChangeRequest(email.value, password))
                }else if(selectedType.value == "Teacher"){
                    backend.changeTeacherPassword(PasswordChangeRequest(email.value, password))
                }else{
                    backend.changeAdminPassword(PasswordChangeRequest(email.value, password))
                }
                Toast.makeText(context,"Password Change Successful", Toast.LENGTH_SHORT).show()
                BackToLoginScreen(navController)
            }
            catch (e:Exception){
                Toast.makeText(context,"Could not change password. Error - ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}