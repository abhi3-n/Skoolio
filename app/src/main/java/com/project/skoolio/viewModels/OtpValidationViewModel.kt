package com.project.skoolio.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.EmailOtpRequest
import com.project.skoolio.model.EmailOtpResponse
import com.project.skoolio.repositories.OtpValidationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class OtpValidationViewModel @Inject constructor(private  val  otpValidationRepository: OtpValidationRepository):ViewModel() {
    private var _otpResponse: MutableState<DataOrException<EmailOtpResponse, Boolean, Exception>> =
        mutableStateOf<DataOrException<EmailOtpResponse, Boolean, Exception>>(
            DataOrException(EmailOtpResponse(""), false, null)
        )
    val otpResponse: State<DataOrException<EmailOtpResponse, Boolean, Exception>> = _otpResponse
    private val _isOtpValidated = mutableStateOf(false)
    fun receiveOTP(
        emailOtpRequest: EmailOtpRequest,
        context: Context,
        navController: NavHostController,
        onOtpSuccess: (Context, NavHostController) -> Unit,
        onOtpFailure: (Context) -> Unit,
        progressIndicatorLoading: MutableState<Boolean>
    ):Unit{
        viewModelScope.launch {
            _otpResponse.value.loading = true
            Log.d("OtpResponse","Loading turned to true.")
            _otpResponse.value = otpValidationRepository.receiveOTP(emailOtpRequest)
            progressIndicatorLoading.value = false
            if(_otpResponse.value.data.otp.isNotEmpty() == true) {
                _otpResponse.value.loading = false
                onOtpSuccess(context,navController)
                Log.d("OtpResponse","Loading turned to false. Otp - "+ _otpResponse.value.data.otp)
            }
            else if(_otpResponse.value.exception != null){
                _otpResponse.value.loading = false
                onOtpFailure(context)
                resetException()
            }
        }
    }

    fun validateOtp(otp:String):Boolean{
         if(otp == _otpResponse.value.data.otp){
             _isOtpValidated.value = true
         }
        else{
            _isOtpValidated.value = false
         }
        return _isOtpValidated.value
    }

    fun getOtp(): String {
        return _otpResponse.value.data.otp
    }
    fun clearStoredOtp() {
        _otpResponse.value.data.otp = ""
    }

    fun getIsOtpValidated(): Boolean {
        return _isOtpValidated.value
    }
    fun resetOtpValidated():Unit{
        _isOtpValidated.value = false
    }
    fun getException(): Exception? {
        return _otpResponse.value.exception
    }

    fun resetException() {
        otpValidationRepository.resetException()
        _otpResponse.value.exception = null
    }
}